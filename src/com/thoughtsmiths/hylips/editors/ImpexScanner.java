package com.thoughtsmiths.hylips.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * 
 * @author Shahnaz Khan [mailtoshahnaz@yahoo.co.in]
 *
 */
public class ImpexScanner extends RuleBasedScanner {
	public final static String IMPEX_MACRO = "__impex_macro";
	public final static String IMPEX_INSERT = "__impex_insert";
	public final static String IMPEX_UPDATE = "__impex_update";
	public final static String IMPEX_REMOVE = "__impex_remove";
	
	
	public ImpexScanner(ColorManager manager) {
		IToken procInstr =
			new Token(
				new TextAttribute(
					manager.getColor(IImpexColorConstants.PROC_INSTR)));
		IToken macro = 
				new Token(
						new TextAttribute(
								manager.getColor(IImpexColorConstants.MACRO)));
		IToken insertMode =  
				new Token(
						new TextAttribute(
								manager.getColor(IImpexColorConstants.INSERTMODE)));
		IToken updateMode =  
				new Token(
						new TextAttribute(
								manager.getColor(IImpexColorConstants.UPDATEMODE)));
		IToken removeMode =  
				new Token(
						new TextAttribute(
								manager.getColor(IImpexColorConstants.REMOVEMODE)));
		
		IRule[] rules = new IRule[6];
		//Add rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new ImpexWhitespaceDetector());
		//rules[2] = new ImpexMacroRule("$", macro);
		//rules[2] = new ImpexMacroRule2("$", macro);
		rules[2] = new SingleLineRule("$", ";", macro);
		rules[3] = new SingleLineRule("INSERT", " ", insertMode);
		rules[4] = new SingleLineRule("UPDATE", " ", updateMode);
		rules[5] = new SingleLineRule("REMOVE", " ", removeMode);
		//rules[6] = new SingleLineRule("$", ")", macro);
		setRules(rules);
	}
}
