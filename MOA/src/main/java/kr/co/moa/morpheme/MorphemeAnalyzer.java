package kr.co.moa.morpheme;

import java.util.List;

import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.TermNode;

public class MorphemeAnalyzer {
	int i=0;
	List<TermNode> result = Analyzer.parseJava("�ƹ������濡���Ŵ�.");
	
	public void doAnalyze(){
		for (TermNode term: result) {
		    System.out.println(term);
		}
	}
		
}
