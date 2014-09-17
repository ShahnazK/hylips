package com.thoughtsmiths.hylips.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

/**
 * This Processor provides the auto completion and suggestion for impex file formats 
 * @author Shahnaz Khan [mailtoshahnaz@yahoo.co.in]
 *
 */
public class ImpexModeContentAssitantProcessor implements IContentAssistProcessor{

	private String[] impexModeProposals = new String[] {"INSERT_UPDATE","UPDATE", "INSERT", "REMOVE"};
	@Override
	public ICompletionProposal[] computeCompletionProposals(
		ITextViewer paramITextViewer, int documentOffset) {
		// code has to optimized
		final IDocument document = paramITextViewer.getDocument();
		final String docContent = document.get();		
		final List<String> proposals = new ArrayList<String>();
		try {
			IRegion currentRegion = document.getLineInformationOfOffset(documentOffset);
			final String currentLine = docContent.substring(currentRegion.getOffset(),currentRegion.getOffset()+currentRegion.getLength());
			final String[] tokens = currentLine.split(" ");
			final String currentWord = tokens[tokens.length-1];
			for (String mode : impexModeProposals) {
				if(mode.toLowerCase().startsWith(currentWord.toLowerCase()))
					proposals.add(mode);			
			}
			
			if(proposals.size()>0){
				final ICompletionProposal[] result = new ICompletionProposal[proposals.size()];
				int count=0;
				for (String proposal : proposals) {
					result[count++] = new CompletionProposal(proposal, documentOffset-currentWord.length(),currentWord.length(), proposal.length());;
				}
				return result;
			}
			return null;
		

		} catch (BadLocationException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public IContextInformation[] computeContextInformation(
			ITextViewer paramITextViewer, int paramInt) {
/*		final IContextInformation[] result = new IContextInformation[impexModeProposals.length];
		int count=0;
		for (String proposal : impexModeProposals) {
			result[count++] = new ContextInformation(proposal, proposal);
		}
		return result;
*/	
	return null;	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[]{'I','U','R'};
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[]{'I','U','R'};
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return "Oops!!! somewthing went wrong inside!";
	}


}
