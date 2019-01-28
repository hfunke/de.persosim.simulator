package de.persosim.simulator.protocols.ta; 
/*
 * AUTOMATICALLY GENERATED CODE - DO NOT EDIT!
 * 
 * (C) secunet Security Networks AG
 */
 @SuppressWarnings("all")//generated code 

/* Command line options: -verbose -p EA -o DefaultTaProtocol -l java -t TA:taclass C:\develop\eclipse-persosim\git\de.persosim.models\exported\protocol_ta.xml   */
/* This file is generated from protocol_ta.xml - do not edit manually  */
/* Generated on: Wed Jul 30 09:45:19 CEST 2014 / version 3.52beta2 */



public class DefaultTaProtocol extends AbstractTaProtocol
{

	public static final int TA_IN_PROGRESS = 0;
	public static final int TA_SET_DST_RECEIVED = 1;
	public static final int TA_COMPLETE = 2;
	public static final int TA_PROGRESS_INIT = 3;
	public static final int TA_GET_CHALLENGE_RECEIVED = 4;
	public static final int REGISTER_APDU_SET_DST = 5;
	public static final int TA_SET_AT_RECEIVED = 6;
	public static final int TA_EXTERNAL_AUTHENTICATE_RECEIVED = 7;
	public static final int TA_INIT = 8;
	public static final int TA_GET_CHALLENGE_PROCESSED = 9;
	public static final int TA_EXTERNAL_AUTHENTICATE_PROCESSED = 10;
	public static final int TA_PSO_RECEIVED = 11;
	public static final int REGISTER_APDUS = 12;
	public static final int REGISTER_APDU_EXTERNAL_AUTHENTICATE = 13;
	public static final int REGISTER_APDU_PSO = 14;
	public static final int TA_ANNOUNCED = 15;
	public static final int REGISTER_APDU_SET_AT = 16;
	public static final int TA_PSO_PROCESSED = 17;
	public static final int TA_SET_AT_PROCESSED = 18;
	public static final int REGISTER_APDU_GET_CHALLENGE = 19;
	public static final int RESET = 20;
	public static final int TA_SET_DST_PROCESSED = 21;
	public static final int __UNKNOWN_STATE__ = 22;


	public static final int DEFAULTTAPROTOCOL_NO_MSG = 0;
	


	// flag if initialized
	protected boolean m_initialized=false;

	int  stateVar;
	int  stateVarREGISTER_APDUS;
	int  stateVarTA_ANNOUNCED;
	int  stateVarTA_IN_PROGRESS;

	// State handler class default ctor
	public DefaultTaProtocol()
	{
	}

	/* Helper(s) to reset history */
	public void resetHistoryREGISTER_APDUS(){stateVarREGISTER_APDUS= REGISTER_APDU_SET_DST;}
	public void resetHistoryTA_ANNOUNCED(){stateVarTA_ANNOUNCED= TA_INIT;}
	public void resetHistoryTA_IN_PROGRESS(){stateVarTA_IN_PROGRESS= TA_PROGRESS_INIT;}

	/* Helper to get innermost active state id */
	public int getInnermostActiveState() {
		if(isInTA_SET_DST_PROCESSED()){
			return TA_SET_DST_PROCESSED;
		}else if(isInTA_SET_AT_PROCESSED()){
			return TA_SET_AT_PROCESSED;
		}else if(isInTA_PSO_PROCESSED()){
			return TA_PSO_PROCESSED;
		}else if(isInTA_PSO_RECEIVED()){
			return TA_PSO_RECEIVED;
		}else if(isInTA_EXTERNAL_AUTHENTICATE_PROCESSED()){
			return TA_EXTERNAL_AUTHENTICATE_PROCESSED;
		}else if(isInTA_EXTERNAL_AUTHENTICATE_RECEIVED()){
			return TA_EXTERNAL_AUTHENTICATE_RECEIVED;
		}else if(isInTA_SET_AT_RECEIVED()){
			return TA_SET_AT_RECEIVED;
		}else if(isInTA_PROGRESS_INIT()){
			return TA_PROGRESS_INIT;
		}else if(isInTA_SET_DST_RECEIVED()){
			return TA_SET_DST_RECEIVED;
		}else if(isInREGISTER_APDU_GET_CHALLENGE()){
			return REGISTER_APDU_GET_CHALLENGE;
		}else if(isInREGISTER_APDU_SET_AT()){
			return REGISTER_APDU_SET_AT;
		}else if(isInREGISTER_APDU_PSO()){
			return REGISTER_APDU_PSO;
		}else if(isInREGISTER_APDU_EXTERNAL_AUTHENTICATE()){
			return REGISTER_APDU_EXTERNAL_AUTHENTICATE;
		}else if(isInTA_GET_CHALLENGE_PROCESSED()){
			return TA_GET_CHALLENGE_PROCESSED;
		}else if(isInTA_INIT()){
			return TA_INIT;
		}else if(isInREGISTER_APDU_SET_DST()){
			return REGISTER_APDU_SET_DST;
		}else if(isInTA_GET_CHALLENGE_RECEIVED()){
			return TA_GET_CHALLENGE_RECEIVED;
		}else if(isInRESET()){
			return RESET;
		}else if(isInTA_COMPLETE()){
			return TA_COMPLETE;
		}else{
			return __UNKNOWN_STATE__;
		}
	}

	// Helper(s) to find out if the machine is in a certain state
	public boolean isInTA_IN_PROGRESS(){return (((stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_SET_DST_RECEIVED(){return (((stateVarTA_IN_PROGRESS==  TA_SET_DST_RECEIVED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_COMPLETE(){return (((stateVar==  TA_COMPLETE)) ? (true) : (false));}
	public boolean isInTA_PROGRESS_INIT(){return (((stateVarTA_IN_PROGRESS==  TA_PROGRESS_INIT)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_GET_CHALLENGE_RECEIVED(){return (((stateVarTA_ANNOUNCED==  TA_GET_CHALLENGE_RECEIVED)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInREGISTER_APDU_SET_DST(){return (((stateVarREGISTER_APDUS==  REGISTER_APDU_SET_DST)&&(stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInTA_SET_AT_RECEIVED(){return (((stateVarTA_IN_PROGRESS==  TA_SET_AT_RECEIVED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_EXTERNAL_AUTHENTICATE_RECEIVED(){return (((stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_RECEIVED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_INIT(){return (((stateVarTA_ANNOUNCED==  TA_INIT)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_GET_CHALLENGE_PROCESSED(){return (((stateVarTA_ANNOUNCED==  TA_GET_CHALLENGE_PROCESSED)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_EXTERNAL_AUTHENTICATE_PROCESSED(){return (((stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_PROCESSED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_PSO_RECEIVED(){return (((stateVarTA_IN_PROGRESS==  TA_PSO_RECEIVED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInREGISTER_APDUS(){return (((stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInREGISTER_APDU_EXTERNAL_AUTHENTICATE(){return (((stateVarREGISTER_APDUS==  REGISTER_APDU_EXTERNAL_AUTHENTICATE)&&(stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInREGISTER_APDU_PSO(){return (((stateVarREGISTER_APDUS==  REGISTER_APDU_PSO)&&(stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInTA_ANNOUNCED(){return (((stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInREGISTER_APDU_SET_AT(){return (((stateVarREGISTER_APDUS==  REGISTER_APDU_SET_AT)&&(stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInTA_PSO_PROCESSED(){return (((stateVarTA_IN_PROGRESS==  TA_PSO_PROCESSED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInTA_SET_AT_PROCESSED(){return (((stateVarTA_IN_PROGRESS==  TA_SET_AT_PROCESSED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}
	public boolean isInREGISTER_APDU_GET_CHALLENGE(){return (((stateVarREGISTER_APDUS==  REGISTER_APDU_GET_CHALLENGE)&&(stateVar==  REGISTER_APDUS)) ? (true) : (false));}
	public boolean isInRESET(){return (((stateVar==  RESET)) ? (true) : (false));}
	public boolean isInTA_SET_DST_PROCESSED(){return (((stateVarTA_IN_PROGRESS==  TA_SET_DST_PROCESSED)&&(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS)&&(stateVar==  TA_ANNOUNCED)) ? (true) : (false));}




	// Reinitialize the state machine
	public void reInitialize(){
			m_initialized=false;
			initialize();
	}

	public void initialize(){

		if(m_initialized==false){

			m_initialized=true;
			//call on entry code of default states
			

			// Set state vars to default states
			stateVar =  REGISTER_APDUS; /* set init state of top state */
			stateVarREGISTER_APDUS =  REGISTER_APDU_SET_DST; /* set init state of REGISTER_APDUS */
			stateVarTA_ANNOUNCED =  TA_INIT; /* set init state of TA_ANNOUNCED */
			stateVarTA_IN_PROGRESS =  TA_PROGRESS_INIT; /* set init state of TA_IN_PROGRESS */

		}

	}

	protected void defaulttaprotocolChangeToState(int  state){
		stateVar=state;
	}

	protected void defaulttaprotocolChangeToStateREGISTER_APDUS(int  state){
		stateVarREGISTER_APDUS = state;
	}
	
	protected void defaulttaprotocolChangeToStateTA_ANNOUNCED(int  state){
		stateVarTA_ANNOUNCED = state;
	}
	
	protected void defaulttaprotocolChangeToStateTA_IN_PROGRESS(int  state){
		stateVarTA_IN_PROGRESS = state;
	}
	



	public int processEvent(int msg){

		int evConsumed = 0;

		

		if(m_initialized==false) return 0;

		/* action code */
		this.continueProcessing = true;
		do{
		evConsumed = 0;


		switch (stateVar) {

			case REGISTER_APDUS:

				switch (stateVarREGISTER_APDUS) {

					case REGISTER_APDU_EXTERNAL_AUTHENTICATE:
						/* action code  */
						createNewApduSpecification("External Authenticate");
						apduSpecification.setIsoFormat(ISO_FORMAT_FIRSTINTERINDUSTRY);
						apduSpecification.setIsoCase(ISO_CASE_3);
						apduSpecification.setIns(INS_82_EXTERNAL_AUTHENTICATE);
						apduSpecification.setP1((byte) 0x00);
						apduSpecification.setP2((byte) 0x00);
						registerApduSpecification(apduSpecification);


						if(true){
							/* Transition from REGISTER_APDU_EXTERNAL_AUTHENTICATE to TA_ANNOUNCED */
							evConsumed=16;


							/* OnEntry code of state TA_ANNOUNCED */
							logs("TA_ANNOUNCED");
							logs("INIT");
							returnResult();
							stateVar =  TA_ANNOUNCED;/* Default in entry chain  */
							stateVarTA_ANNOUNCED =  TA_INIT;/* Default in entry chain  */

							/* adjust state variables  */
							stateVarREGISTER_APDUS =  REGISTER_APDU_SET_DST;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case REGISTER_APDU_EXTERNAL_AUTHENTICATE  */

					case REGISTER_APDU_GET_CHALLENGE:
						/* action code  */
						createNewApduSpecification("Get Challenge");
						apduSpecification.setIsoFormat(ISO_FORMAT_FIRSTINTERINDUSTRY);
						apduSpecification.setIsoCase(ISO_CASE_2);
						apduSpecification.setIns(INS_84_GET_CHALLENGE);
						apduSpecification.setP1((byte) 0x00);
						apduSpecification.setP2((byte) 0x00);
						registerApduSpecification(apduSpecification);


						if(true){
							/* Transition from REGISTER_APDU_GET_CHALLENGE to REGISTER_APDU_EXTERNAL_AUTHENTICATE */
							evConsumed=16;


							/* adjust state variables  */
							stateVarREGISTER_APDUS =  REGISTER_APDU_EXTERNAL_AUTHENTICATE;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case REGISTER_APDU_GET_CHALLENGE  */

					case REGISTER_APDU_PSO:
						/* action code  */
						createNewApduSpecification("PSO");
						apduSpecification.setIsoFormat(ISO_FORMAT_FIRSTINTERINDUSTRY);
						apduSpecification.setIsoCase(ISO_CASE_3);
						apduSpecification.setIns(INS_2A_PERFORM_SECURITY_OPERATION);
						apduSpecification.setP1((byte) 0x00);
						apduSpecification.setP2((byte) 0xBE);
						createNewTagSpecification(TAG_7F4E);
						tagSpecification.setAllowUnspecifiedSubTags(true);
						apduSpecification.addTag(tagSpecification);
						createNewTagSpecification(TAG_5F37);
						tagSpecification.setAllowUnspecifiedSubTags(true);
						apduSpecification.addTag(tagSpecification);
						registerApduSpecification(apduSpecification);


						if(true){
							/* Transition from REGISTER_APDU_PSO to REGISTER_APDU_SET_AT */
							evConsumed=16;


							/* adjust state variables  */
							stateVarREGISTER_APDUS =  REGISTER_APDU_SET_AT;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case REGISTER_APDU_PSO  */

					case REGISTER_APDU_SET_AT:
						/* action code  */
						createNewApduSpecification("Set AT");
						apduSpecification.setIsoFormat(ISO_FORMAT_FIRSTINTERINDUSTRY);
						apduSpecification.setIsoCase(ISO_CASE_3);
						apduSpecification.setIns(INS_22_MANAGE_SECURITY_ENVIRONMENT);
						apduSpecification.setP1((byte) 0x81);
						apduSpecification.setP2((byte) 0xA4);
						createNewTagSpecification(TAG_80);
						apduSpecification.addTag(tagSpecification);
						createNewTagSpecification(TAG_83);
						apduSpecification.addTag(tagSpecification);
						createNewTagSpecification(TAG_67);
						tagSpecification.setRequired(REQ_OPTIONAL);
						tagSpecification.setAllowUnspecifiedSubTags(true);
						apduSpecification.addTag(tagSpecification);
						createNewTagSpecification(TAG_91);
						tagSpecification.setRequired(REQ_OPTIONAL);
						apduSpecification.addTag(tagSpecification);
						registerApduSpecification(apduSpecification);


						if(true){
							/* Transition from REGISTER_APDU_SET_AT to REGISTER_APDU_GET_CHALLENGE */
							evConsumed=16;


							/* adjust state variables  */
							stateVarREGISTER_APDUS =  REGISTER_APDU_GET_CHALLENGE;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case REGISTER_APDU_SET_AT  */

					case REGISTER_APDU_SET_DST:
						/* action code  */
						createNewApduSpecification("Set DST");
						apduSpecification.setIsoFormat(ISO_FORMAT_FIRSTINTERINDUSTRY);
						apduSpecification.setIsoCase(ISO_CASE_3);
						apduSpecification.setIns(INS_22_MANAGE_SECURITY_ENVIRONMENT);
						apduSpecification.setP1((byte) 0x81);
						apduSpecification.setP2((byte) 0xB6);
						createNewTagSpecification(TAG_83);
						apduSpecification.addTag(tagSpecification);
						registerApduSpecification(apduSpecification);


						if(true){
							/* Transition from REGISTER_APDU_SET_DST to REGISTER_APDU_PSO */
							evConsumed=16;


							/* adjust state variables  */
							stateVarREGISTER_APDUS =  REGISTER_APDU_PSO;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case REGISTER_APDU_SET_DST  */

					default:
						/* Intentionally left blank */
					break;
				} /* end switch REGISTER_APDUS */
			break; /* end of case REGISTER_APDUS  */

			case RESET:
				if(true){
					/* Transition from RESET to TA_INIT */
					evConsumed=16;

					/* OnEntry code of state TA_ANNOUNCED */
					logs("TA_ANNOUNCED");

					/* OnEntry code of state TA_INIT */
					logs("INIT");
					returnResult();

					/* adjust state variables  */
					stateVar =  TA_ANNOUNCED;
					stateVarTA_ANNOUNCED =  TA_INIT;
				}else{
					/* Intentionally left blank */
				} /*end of event selection */
			break; /* end of case RESET  */

			case TA_ANNOUNCED:

				switch (stateVarTA_ANNOUNCED) {

					case TA_GET_CHALLENGE_PROCESSED:
						if(true){
							/* Transition from TA_GET_CHALLENGE_PROCESSED to TA_IN_PROGRESS */
							evConsumed=16;

							/* Action code for transition  */
							returnResult();

							stateVarTA_ANNOUNCED =  TA_IN_PROGRESS;/* entry chain  */
							if(stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_RECEIVED){
								processCommandExternalAuthenticate();

							}else if(stateVarTA_IN_PROGRESS==  TA_PSO_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_PSO_RECEIVED){
								processCommandPsoVerifyCertificate();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_AT_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_AT_RECEIVED){
								processCommandSetAt();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_DST_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_DST_RECEIVED){
								processCommandSetDst();

							}

						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case TA_GET_CHALLENGE_PROCESSED  */

					case TA_GET_CHALLENGE_RECEIVED:
						if(isStatusWord(SW_9000_NO_ERROR)){
							/* Transition from TA_GET_CHALLENGE_RECEIVED to TA_GET_CHALLENGE_PROCESSED */
							evConsumed=16;


							/* adjust state variables  */
							stateVarTA_ANNOUNCED =  TA_GET_CHALLENGE_PROCESSED;
						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case TA_GET_CHALLENGE_RECEIVED  */

					case TA_INIT:
						if(true){
							/* Transition from TA_INIT to TA_IN_PROGRESS */
							evConsumed=16;

							stateVarTA_ANNOUNCED =  TA_IN_PROGRESS;/* entry chain  */
							if(stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_EXTERNAL_AUTHENTICATE_RECEIVED){
								processCommandExternalAuthenticate();

							}else if(stateVarTA_IN_PROGRESS==  TA_PSO_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_PSO_RECEIVED){
								processCommandPsoVerifyCertificate();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_AT_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_AT_RECEIVED){
								processCommandSetAt();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_DST_PROCESSED){
								returnResult();

							}else if(stateVarTA_IN_PROGRESS==  TA_SET_DST_RECEIVED){
								processCommandSetDst();

							}

						}else{
							/* Intentionally left blank */
						} /*end of event selection */
					break; /* end of case TA_INIT  */

					case TA_IN_PROGRESS:

						switch (stateVarTA_IN_PROGRESS) {

							case TA_EXTERNAL_AUTHENTICATE_PROCESSED:
								if(true){
									/* Transition from TA_EXTERNAL_AUTHENTICATE_PROCESSED to TA_COMPLETE */
									evConsumed=16;



									/* OnEntry code of state TA_COMPLETE */
									logs("Completed");

									/* adjust state variables  */
									stateVar =  TA_COMPLETE;
									stateVarTA_ANNOUNCED =  TA_INIT;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_EXTERNAL_AUTHENTICATE_PROCESSED  */

							case TA_EXTERNAL_AUTHENTICATE_RECEIVED:
								if(isStatusWord(SW_9000_NO_ERROR)){
									/* Transition from TA_EXTERNAL_AUTHENTICATE_RECEIVED to TA_EXTERNAL_AUTHENTICATE_PROCESSED */
									evConsumed=16;

									/* OnEntry code of state TA_EXTERNAL_AUTHENTICATE_PROCESSED */
									returnResult();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_EXTERNAL_AUTHENTICATE_PROCESSED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_EXTERNAL_AUTHENTICATE_RECEIVED  */

							case TA_PROGRESS_INIT:
								if(isAPDU("Set DST")){
									/* Transition from TA_PROGRESS_INIT to TA_SET_DST_RECEIVED */
									evConsumed=16;

									/* OnEntry code of state TA_SET_DST_RECEIVED */
									processCommandSetDst();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_SET_DST_RECEIVED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_PROGRESS_INIT  */

							case TA_PSO_PROCESSED:
								if(isAPDU("Set AT")){
									/* Transition from TA_PSO_PROCESSED to TA_SET_AT_RECEIVED */
									evConsumed=16;

									/* OnEntry code of state TA_SET_AT_RECEIVED */
									processCommandSetAt();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_SET_AT_RECEIVED;
								}else if(isAPDU("Set DST")){
									/* Transition from TA_PSO_PROCESSED to TA_SET_DST_RECEIVED */
									evConsumed=16;

									/* OnEntry code of state TA_SET_DST_RECEIVED */
									processCommandSetDst();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_SET_DST_RECEIVED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_PSO_PROCESSED  */

							case TA_PSO_RECEIVED:
								if(isStatusWord(SW_9000_NO_ERROR)){
									/* Transition from TA_PSO_RECEIVED to TA_PSO_PROCESSED */
									evConsumed=16;

									/* OnEntry code of state TA_PSO_PROCESSED */
									returnResult();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_PSO_PROCESSED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_PSO_RECEIVED  */

							case TA_SET_AT_PROCESSED:
								if(isAPDU("External Authenticate")){
									/* Transition from TA_SET_AT_PROCESSED to TA_EXTERNAL_AUTHENTICATE_RECEIVED */
									evConsumed=16;

									/* OnEntry code of state TA_EXTERNAL_AUTHENTICATE_RECEIVED */
									processCommandExternalAuthenticate();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_EXTERNAL_AUTHENTICATE_RECEIVED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_SET_AT_PROCESSED  */

							case TA_SET_AT_RECEIVED:
								if(isStatusWord(SW_9000_NO_ERROR)){
									/* Transition from TA_SET_AT_RECEIVED to TA_SET_AT_PROCESSED */
									evConsumed=16;

									/* OnEntry code of state TA_SET_AT_PROCESSED */
									returnResult();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_SET_AT_PROCESSED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_SET_AT_RECEIVED  */

							case TA_SET_DST_PROCESSED:
								if(isAPDU("PSO")){
									/* Transition from TA_SET_DST_PROCESSED to TA_PSO_RECEIVED */
									evConsumed=16;

									/* OnEntry code of state TA_PSO_RECEIVED */
									processCommandPsoVerifyCertificate();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_PSO_RECEIVED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_SET_DST_PROCESSED  */

							case TA_SET_DST_RECEIVED:
								if(isStatusWord(SW_9000_NO_ERROR)){
									/* Transition from TA_SET_DST_RECEIVED to TA_SET_DST_PROCESSED */
									evConsumed=16;

									/* OnEntry code of state TA_SET_DST_PROCESSED */
									returnResult();

									/* adjust state variables  */
									stateVarTA_IN_PROGRESS =  TA_SET_DST_PROCESSED;
								}else{
									/* Intentionally left blank */
								} /*end of event selection */
							break; /* end of case TA_SET_DST_RECEIVED  */

							default:
								/* Intentionally left blank */
							break;
						} /* end switch TA_IN_PROGRESS */

						/* Check if event was already processed  */
						if(evConsumed==0){

							if(isAPDU("Get Challenge")){
								/* Transition from TA_IN_PROGRESS to TA_GET_CHALLENGE_RECEIVED */
								evConsumed=16;
								
								/* OnEntry code of state TA_GET_CHALLENGE_RECEIVED */
								processCommandGetChallenge();

								/* adjust state variables  */
								stateVarTA_ANNOUNCED =  TA_GET_CHALLENGE_RECEIVED;
							}else{
								/* Intentionally left blank */
							} /*end of event selection */
						}
					break; /* end of case TA_IN_PROGRESS  */

					default:
						/* Intentionally left blank */
					break;
				} /* end switch TA_ANNOUNCED */

				/* Check if event was already processed  */
				if(evConsumed==0){

					if(true){
						if(warningOrErrorOccurredDuringProcessing()){
							/* Transition from TA_ANNOUNCED to RESET */
							evConsumed=16;
						
							if(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS){
								
							}

							/* OnEntry code of state RESET */
							logs("Perform reset");
							reset();

							/* adjust state variables  */
							stateVar =  RESET;
						}else{
							/* Transition from TA_ANNOUNCED to TA_ANNOUNCED */
							evConsumed=16;
						
							if(stateVarTA_ANNOUNCED==  TA_IN_PROGRESS){
								
							}

							/* OnEntry code of state TA_ANNOUNCED */
							logs("TA_ANNOUNCED");
							logs("INIT");
							returnResult();
							stateVar =  TA_ANNOUNCED;/* Default in entry chain  */
							stateVarTA_ANNOUNCED =  TA_INIT;/* Default in entry chain  */

						} /*end of event selection */
					}else{
						/* Intentionally left blank */
					} /*end of event selection */
				}
			break; /* end of case TA_ANNOUNCED  */

			case TA_COMPLETE:
				if(true){
					/* Transition from TA_COMPLETE to TA_INIT */
					evConsumed=16;

					/* OnEntry code of state TA_ANNOUNCED */
					logs("TA_ANNOUNCED");

					/* OnEntry code of state TA_INIT */
					logs("INIT");
					returnResult();

					/* adjust state variables  */
					stateVar =  TA_ANNOUNCED;
					stateVarTA_ANNOUNCED =  TA_INIT;
				}else{
					/* Intentionally left blank */
				} /*end of event selection */
			break; /* end of case TA_COMPLETE  */

			default:
				/* Intentionally left blank */
			break;
		} /* end switch stateVar_root */

	/* Post Action Code */
	} while (this.continueProcessing);
		return evConsumed;
	}
}
