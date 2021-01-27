package com.example;

import java.util.ArrayList;
import java.util.List;

public class GitFileGroup {
	private List<GitFile> GitFiles; 
	private int AllBytes ;
	private int AllLines ;
	private String FileTypeGroup;
	
	public GitFileGroup()
	{
		GitFiles = new ArrayList<GitFile>();
	}
	
	public void AddGitFiles(GitFile gitFile) {
		this.GitFiles.add(gitFile);
	}
	
	public void calculateAllBytes()
	{
		for (GitFile gitFiles : this.GitFiles)
		{
			this.AllBytes += gitFiles.getBytes();
		}
	}
	public void calculateAllLines()
	{
		for (GitFile gitFiles : this.GitFiles)
		{
			this.AllLines += gitFiles.getLines();
		}
	}
	public String getFileTypeGroup() {
		return FileTypeGroup;
	}
	public void setFileTypeGroup(String fileTypeGroup) {
		FileTypeGroup = fileTypeGroup;
	}
	
	

	public int getAllBytes() {
		return AllBytes;
	}

	public void setAllBytes(int allBytes) {
		AllBytes = allBytes;
	}

	public int getAllLines() {
		return AllLines;
	}

	public void setAllLines(int allLines) {
		AllLines = allLines;
	}
	public List<GitFile> getGitFiles() {
		return GitFiles;
	}
	public void setGitFiles(List<GitFile> gitFiles) {
		GitFiles = gitFiles;
	}
}

