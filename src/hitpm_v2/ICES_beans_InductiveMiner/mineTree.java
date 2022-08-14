package hitpm_v2.ICES_beans_InductiveMiner;


import org.deckfour.xes.model.XLog;

import org.processmining.framework.packages.PackageManager.Canceller;

import org.processmining.plugins.InductiveMiner.efficienttree.EfficientTree;
import org.processmining.plugins.inductiveminer2.logs.IMLog;
import org.processmining.plugins.inductiveminer2.mining.InductiveMiner;
import org.processmining.plugins.inductiveminer2.mining.MiningParameters;
//import org.processmining.plugins.inductiveminer2.plugins.InductiveMinerDialog;

public class mineTree {
	public EfficientTree mine(XLog xLog) {
		InductiveMinerDialog dialog = new InductiveMinerDialog(xLog);
//		InteractionResult result = context.showWizard("Mine using Inductive Miner", true, true, dialog);

//		if (result != InteractionResult.FINISHED) {
//			context.getFutureResult(0).cancel(false);
//			return null;
//		}

		MiningParameters parameters = dialog.getMiningParameters();
		IMLog log = parameters.getIMLog(xLog);

		//check that the log is not too big and mining might take a long time
//		if (!confirmLargeLogs(context, log, dialog)) {
//			context.getFutureResult(0).cancel(false);
//			return null;
//		}

//		context.log("Mining...");

		return InductiveMiner.mineEfficientTree(log, parameters, new Canceller() {
			public boolean isCancelled() {
				return false;
			}
		});
	}
	
}
