package com.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.GitFile;
import com.example.GitFileGroup;

class ClassesTest {

	@Test
	void GitFileCompareTest() {
		List<GitFile> GitFiles = new ArrayList<GitFile>();
		
		GitFile gitFile = new GitFile();
		gitFile.setType("d");
		GitFiles.add(gitFile);
		
		gitFile = new GitFile();
		gitFile.setType("c");
		GitFiles.add(gitFile);
		
		gitFile = new GitFile();
		gitFile.setType("b");
		GitFiles.add(gitFile);
		
		gitFile = new GitFile();
		gitFile.setType("a");
		GitFiles.add(gitFile);
		
		Collections.sort(GitFiles);
		assertEquals("a", GitFiles.get(0).getType(),"alfabetic order");
		assertEquals("b", GitFiles.get(1).getType(),"alfabetic order");
		assertEquals("c", GitFiles.get(2).getType(),"alfabetic order");
		assertEquals("d", GitFiles.get(3).getType(),"alfabetic order");
	}
	
	@Test
	void GitFileGroupAddGitFiles() {
		GitFileGroup gitFileGroup = new GitFileGroup();
		GitFile gitFile = new GitFile();
		
		gitFileGroup.AddGitFiles(gitFile);
		assertEquals(1, gitFileGroup.getGitFiles().size());
	}
	
	@Test
	void GitFileGroupCalculateAllBytes() {
		GitFileGroup gitFileGroup = new GitFileGroup();
		GitFile gitFile = new GitFile();
		gitFile.setBytes(2);
		gitFileGroup.AddGitFiles(gitFile);
		
		
		gitFile = new GitFile();
		gitFile.setBytes(3);
		gitFileGroup.AddGitFiles(gitFile);
		
		gitFileGroup.calculateAllBytes();
		
		assertEquals(5, gitFileGroup.getAllBytes());
	}
	
	@Test
	void GitFileGroupCalculateAllLines() {
		GitFileGroup gitFileGroup = new GitFileGroup();
		GitFile gitFile = new GitFile();
		gitFile.setLines(7);
		gitFileGroup.AddGitFiles(gitFile);
		
		
		gitFile = new GitFile();
		gitFile.setLines(13);
		gitFileGroup.AddGitFiles(gitFile);
		
		gitFileGroup.calculateAllLines();
		
		assertEquals(20, gitFileGroup.getAllLines());
	}
	

}
