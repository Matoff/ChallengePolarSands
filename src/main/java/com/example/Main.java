/*
 * Copyright 2002-2014 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@SpringBootApplication
@RestController
public class Main {
	
	@GetMapping("/GitScrappingFiles")
	public Object GitScrappingFiles(@RequestParam(value = "repository", defaultValue = "") String repository) {
		System.out.println("Resquest variable " + repository);
		if(repository.equals(null) || repository.isEmpty())
		{
			return "repository invalid";
		}
		try {
			System.out.println("Execution Starts");
		
			ChromeOptions options = new ChromeOptions(); 
			
			
			//CONFIG FOR SERVER OR LOCAL
			//Comment Top Part a uncomment Bottom part to work localy
			//Uncomment top part and commnet bottom part to work on HEROKU server
			
			//Top part
			System.setProperty("GOOGLE_CHROME_BIN", "/app/.apt/usr/bin/google-chrome"); 
			System.setProperty("CHROMEDRIVER_PATH", "/app/.chromedriver/bin/chromedriver");
			options.setBinary("/app/.apt/usr/bin/google-chrome");
			
			//Bottom Part
			//System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
			
			//Config the web driver
			options.addArguments("--enable-javascript"); 
			options.addArguments("--headless"); 
			options.addArguments("--disable-gpu"); 
			options.addArguments("--no-sandbox");
			WebDriver driver = new ChromeDriver(options); 
			driver.manage().window().maximize();
			
			System.out.println("Browser opened.");
			JavascriptExecutor js= (JavascriptExecutor) driver;
			String url = repository;
			String script = "window.location = \'"+url+"\'";
			js.executeScript(script);           

			System.out.println(driver.getCurrentUrl());
			//Wait for Js.Ready to get the next page
			boolean documentReady = js.executeScript("return document.readyState").equals("complete");
	    	  //System.out.println("ready Before = " + documentReady );
	    	  while(!documentReady)
	    	  {
	    		  documentReady = js.executeScript("return document.readyState").equals("complete");
	    	  }
	    	  
	    	  //go to "go to file" in github to get list fo all files
	    	  WebElement GoToFile =driver.findElement(By.linkText("Go to file"));
	    	  GoToFile.click();
	    	  //js.Ready dont help on this one, maybe is a request AJAx or Async
	    	  //get list of files
	    	  TimeUnit.SECONDS.sleep(1);
		      WebElement ListFiles =driver.findElement(By.id("tree-browser"));
		      System.out.println(ListFiles.getText());

		      List<WebElement> c = ListFiles.findElements(By.xpath("./child::*"));
		      List<String> LinkFiles = new ArrayList<String>();
		      //Get Links from The list of Files
		      for ( WebElement elemento : c)
		      {
		    	  System.out.println(elemento.findElement(By.tagName("a")).getAttribute("href"));
		    	  LinkFiles.add(elemento.findElement(By.tagName("a")).getAttribute("href"));
		      }
		      //Trasnform into RawLinks and get Type
		      List<GitFile> GitFiles = new ArrayList<GitFile>();
		      for ( String Link : LinkFiles)
		      {
		    	  	//System.out.println(Link.indexOf(".com/")); = 14
		    	  	//14 + 4 = 18
		    	  	System.out.println(Link.substring(18).replace("/blob", ""));
		    	    String PreLink = Link.substring(18).replace("/blob", "");
		    	    
		    	    int fileReferencePosition = PreLink.indexOf(".");
		    	    //System.out.println("indexof: " + fileReferencePosition);
		    	    
		    	    //some files have NO type in their name, like LICENSE in git. This gets them to a unlisted group
		    	    String FileType = "Unlisted";
		    	    if(fileReferencePosition != -1)
		    	    {
		    	    	FileType = PreLink.substring(fileReferencePosition);
		    	    }
		    	    
		    	    String RawUrl = "https://raw.githubusercontent.com" + PreLink;
		    	    //System.out.println("Filetype:" + FileType);
		    	    
		    	    GitFile gitFile = new GitFile();
		    	    gitFile.setRawUrl(RawUrl);
		    	    gitFile.setType(FileType);
		    	    GitFiles.add(gitFile);
		    	    
		    	  	
		      }
		      
		      //ORDER BY TYPE, created in GitFiles Class
		      Collections.sort(GitFiles);
		      
		      
		      //Prepare response
		      GitFilesResponse gitFilesResponse = new GitFilesResponse();
		      GitFileGroup gitFileGroup = new GitFileGroup();
		      for (GitFile gitFile : GitFiles)
		      {
		    	  driver.navigate().to(gitFile.getRawUrl());
		    	  documentReady = js.executeScript("return document.readyState").equals("complete");
		    	  //System.out.println("ready Before = " + documentReady );
		    	  while(!documentReady)
		    	  {
		    		  documentReady = js.executeScript("return document.readyState").equals("complete");
		    	  }
		    	  //System.out.println("ready After = " + documentReady );
		    	  System.out.println(gitFile.getRawUrl());
		    	  //GetRawCode
		    	  WebElement rawCode =driver.findElement(By.tagName("body"));
		    	  
		    	  //Count Lines and size
		    	  String RawText = rawCode.getText();
		    	  String[] lines = RawText.split("\r\n|\r|\n");
		    	  System.out.println("Lines=" + lines.length);  
		    	  int bytes = RawText.getBytes().length;
		    	  System.out.println("size = " + bytes + "bytes");
		    	  
		    	  
		    	  //Prepare object
		    	  
		    	  //Type is Filed
		    	  //RawUrl is Filed
		    	  gitFile.setBytes(bytes);
		    	  gitFile.setLines(lines.length);
		    	  
		    	  //Response have a list of Groups(separated by file types.
		    	  //Groups have a list of Files
		    	  
		    	  //First Group created is null
		    	  if(gitFileGroup.getFileTypeGroup() == null)
		    	  {
		    		  gitFileGroup.setFileTypeGroup(gitFile.getType());
		    		  gitFileGroup.AddGitFiles(gitFile);
		    	  } else 
		    	  {
		    		  //Check to see if is the LAST file of a group, if so, push that group to the response
		    		  //Then create a new group and move on
		    		  if(gitFile.getType().equals(gitFileGroup.getFileTypeGroup()))
		    		  {
		    			  gitFileGroup.AddGitFiles(gitFile);
		    		  } else 
		    		  {
		    			  gitFileGroup.calculateAllBytes();
		    			  gitFileGroup.calculateAllLines();
		    			  gitFilesResponse.AddGitFiles(gitFileGroup);
		    			  gitFileGroup = new GitFileGroup();
		    			  gitFileGroup.setFileTypeGroup(gitFile.getType());
		    			  gitFileGroup.AddGitFiles(gitFile);
		    		  }
		    	  }
		      }
		      
		      //calculate all the bytes from the groups and the response
		      gitFilesResponse.calculateAllBytes();
		      gitFilesResponse.calculateAllLines();
		      
			return gitFilesResponse;
		} catch (Exception e) {
			return (e);
		}
	}

	  public static void main(String[] args) throws Exception {
	    SpringApplication.run(Main.class, args);
	  }

	  @RequestMapping("/")
	  String index() {
	    return "This is an Trustly Technical Challenge for Developers\r\n"
	    		+ "The only route available is /GitScrappingFiles witch receives the variable repository with the link to the public GitHub repository. \r\n"
	    		+ "Example:\r\n"
	    		+ "https://polar-sands-11326.herokuapp.com/GitScrappingFiles?repository=https://github.com/FMGameDev/CMP105_Lab10\r\n" 
	    		+ "The response will be divided by groups of file extensions. All will be listed; their size and line count will be listed individually and its sum.";
	  }
	  

	 


}
