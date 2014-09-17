package com.thoughtsmiths.hylips.editors.rules;

import java.util.Arrays;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class ImpexMacroRule extends EndOfLineRule {

	public ImpexMacroRule(String startSequence, IToken token) {
		super("$", token);
		// TODO Auto-generated constructor stub
	}
	
	protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
		System.out.println("[doEvaluate]");
		//System.out.println("[scanner]"+(char)scanner.read());
		//System.out.println("[Column]"+scanner.getColumn());
		if (resume) {

			if (endSequenceDetected(scanner))
				return fToken;

		} else {

			int c= scanner.read();
			if (c == fStartSequence[0]) {
				if (sequenceDetected(scanner, fStartSequence, false)) {
					if (endSequenceDetected(scanner))
						return fToken;
				}
			}
		}

		scanner.unread();
		return Token.UNDEFINED;
	}
	
	protected boolean sequenceDetected(
			ICharacterScanner scanner,
			char[] sequence,
			boolean eofAllowed) {
		System.out.println("[sequenceDetected]"+new String(sequence));
		for (int i= 1; i < sequence.length; i++) {
			int c= scanner.read();
			if (c == ICharacterScanner.EOF && eofAllowed) {
				return true;
			} else if (c != sequence[i]) {
				// Non-matching character detected, rewind the scanner back to the start.
				// Do not unread the first character.
				scanner.unread();
				for (int j= i-1; j > 0; j--)
					scanner.unread();
				return false;
			}
		}

		return true;
	}


	protected boolean endSequenceDetected(ICharacterScanner scanner) {
		System.out.println("[endSequenceDetected]");
		int c=scanner.read();
		System.out.println((char)c);
		if(c==';'){
			System.out.println("[; detected]");
			//scanner.unread();
			return true;
		}
		return super.endSequenceDetected(scanner);
	}
}
