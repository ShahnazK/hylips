package com.thoughtsmiths.hylips.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class ImpexConfiguration extends SourceViewerConfiguration {
	private ImpexDoubleClickStrategy doubleClickStrategy;
	private ImpexTagScanner tagScanner;
	private ImpexScanner scanner;
	private ColorManager colorManager;

	public ImpexConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			ImpexPartitionScanner.IMPEX_COMMENT,
			ImpexPartitionScanner.IMPEX_TAG};
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new ImpexDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected ImpexScanner getImpexScanner() {
		if (scanner == null) {
			scanner = new ImpexScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IImpexColorConstants.DEFAULT))));
		}
		return scanner;
	}
	protected ImpexTagScanner getImpexTagScanner() {
		if (tagScanner == null) {
			tagScanner = new ImpexTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IImpexColorConstants.TAG))));
		}
		return tagScanner;
	}

	public IContentAssistant getContentAssistant(ISourceViewer sv) {
	    ContentAssistant ca = new ContentAssistant();
	    ca.enableAutoActivation(true);
	    ca.setAutoActivationDelay(500);
	    ca.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
	    ca.enableAutoInsert(true);
	    ca.enablePrefixCompletion(true);
	    IContentAssistProcessor pr = new ImpexModeContentAssitantProcessor();
	    ca.setContentAssistProcessor(pr, IDocument.DEFAULT_CONTENT_TYPE);
	    ca.setInformationControlCreator(getInformationControlCreator(sv));
	    return ca;
	}
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getImpexTagScanner());
		reconciler.setDamager(dr, ImpexPartitionScanner.IMPEX_TAG);
		reconciler.setRepairer(dr, ImpexPartitionScanner.IMPEX_TAG);

		dr = new DefaultDamagerRepairer(getImpexScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(getImpexScanner());
		reconciler.setDamager(dr, ImpexScanner.IMPEX_MACRO);
		reconciler.setRepairer(dr, ImpexScanner.IMPEX_MACRO);

		dr = new DefaultDamagerRepairer(getImpexScanner());
		reconciler.setDamager(dr, ImpexScanner.IMPEX_INSERT);
		reconciler.setRepairer(dr, ImpexScanner.IMPEX_INSERT);
		
		dr = new DefaultDamagerRepairer(getImpexScanner());
		reconciler.setDamager(dr, ImpexScanner.IMPEX_UPDATE);
		reconciler.setRepairer(dr, ImpexScanner.IMPEX_UPDATE);
		
		dr = new DefaultDamagerRepairer(getImpexScanner());
		reconciler.setDamager(dr, ImpexScanner.IMPEX_REMOVE);
		reconciler.setRepairer(dr, ImpexScanner.IMPEX_REMOVE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IImpexColorConstants.IMPEX_COMMENT)));
		reconciler.setDamager(ndr, ImpexPartitionScanner.IMPEX_COMMENT);
		reconciler.setRepairer(ndr, ImpexPartitionScanner.IMPEX_COMMENT);

	return reconciler;
	}

}