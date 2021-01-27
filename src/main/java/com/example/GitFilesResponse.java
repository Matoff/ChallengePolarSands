package com.example;

import java.util.ArrayList;
import java.util.List;

public class GitFilesResponse {
	
	private int AllBytes ;
	private int AllLines ;
	private List<GitFileGroup> GitFileGroups; 
	

	public GitFilesResponse()
	{
		GitFileGroups = new ArrayList<GitFileGroup>();
	}
	
	public void AddGitFiles(GitFileGroup gitFileGroup) {
		this.GitFileGroups.add(gitFileGroup);
	}
	
	public void calculateAllBytes()
	{
		for (GitFileGroup gitFilesGroup : this.GitFileGroups)
		{
			this.AllBytes += gitFilesGroup.getAllBytes();
		}
	}
	public void calculateAllLines()
	{
		for (GitFileGroup gitFilesGroup : this.GitFileGroups)
		{
			this.AllLines += gitFilesGroup.getAllLines();
		}
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

	public List<GitFileGroup> getGitFileGroups() {
		return GitFileGroups;
	}

	public void setGitFileGroups(List<GitFileGroup> gitFileGroups) {
		GitFileGroups = gitFileGroups;
	}
}
