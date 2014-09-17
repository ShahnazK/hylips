package com.thoughtsmiths.hylips.editors;

import org.eclipse.jface.text.rules.*;

public class ImpexPartitionScanner extends RuleBasedPartitionScanner {
	public final static String IMPEX_COMMENT = "__impex_comment";
	public final static String IMPEX_TAG = "__impex_tag";

	public ImpexPartitionScanner() {

		IToken impexComment = new Token(IMPEX_COMMENT);
		IToken tag = new Token(IMPEX_TAG);
		IPredicateRule[] rules = new IPredicateRule[2];

		//rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		//rules[0] = new SingleLineRule("#","",impexComment);
		rules[0] = new EndOfLineRule("#",impexComment);
		rules[1] = new TagRule(tag);
		setPredicateRules(rules);
	}
}
