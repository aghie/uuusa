package org.grupolys.samulan.analyser.operation;

import java.util.ArrayList;
import java.util.List;

import org.grupolys.nlputils.parser.DependencyNode;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public class NChildrenStrategy extends AbstractStrategy implements ScopeStrategy {



		private int n;
		private int reference  = 0;
		private boolean rightSide = false;
		
		public NChildrenStrategy(int n, boolean rightside) {
			super();
			this.n = n;
			this.rightSide = rightside;
		}
		
		
		public int getN() {
			return n;
		}


		public void setN(int n) {
			this.n = n;
		}


		public int getReference() {
			return reference;
		}


		public void setReference(int reference) {
			this.reference = reference;
		}

		/**
		 * 
		 * @param head
		 * @param child
		 * @param operation
		 * @return
		 */
		
		private boolean isOnRightSide(short address){
			
			if (this.rightSide) return address > this.reference;
			return address < this.reference;
		}
		

		/**
		 * 
		 */
		public OperationValue apply(SentimentInformation head, 
									List<SentimentInformation> children,
									Operation operation) {
			
			//System.out.println("Entra NRightBrothersStrategy");
			SentimentInformation auxHead = new SentimentInformation(head);
			SentimentInformation auxChild;
			List<SentimentInformation> newChildren = new ArrayList<SentimentInformation>();
			int i=0;
			for (SentimentInformation siChild : children){
				auxChild = new SentimentInformation(siChild);
				if (i < this.n && (isOnRightSide(siChild.getSentimentDependencyNode().getAddress() ))){
						operation.updateSentiment(auxChild);
						i+=1;
				}
				newChildren.add(auxChild);
			}
			return new OperationValue(auxHead,newChildren);
		}
		
		
		
		
		@Override
		public String toString(){
			String side = this.rightSide ? "Right" : "Left";
			return N_CHILDREN+"("+side+")"+"("+this.n+")";
		}

	
	
	
} 
