package com.franksang.dsapi.vmdsapi;

/**
 * Created by Administrator on 2015/9/29.
 */
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.PointerByReference;

public interface libvmdsapi extends Library {

    public static class time_t extends Structure {
        public NativeLong value;

        public time_t() {
        }

        public time_t(NativeLong values) {
            this.value = values;
        }
    }

    public static class DSJOBINFO extends Structure {
        public int infotype;
        public JOBINFO info;//  = new JOBINFO();
    }

    /*public static class DSJOBINFO1 extends Structure {
        public int infotype;
        //public JOBINFO info;//  = new JOBINFO();
        @SuppressWarnings("unused")
        public static class JOBINFO extends Union {
            public int jobStatus; // Current status of job
            public Pointer jobController;// Name of controlling job
            public time_t jobStartTime; // Date and time when job started
            public int jobWaveNumber;// Wave number of current or last run
            public Pointer userStatus;// Last set user status
            public Pointer stageList; // List of stage names in job
            public Pointer paramList; // List of job parameter names
            public Pointer jobName; // Name of this job
            public int jobControl; // Current job control status
            public int jobPid; // Job process id
            public time_t jobLastTime; // Date and time when job finished
            public Pointer jobInvocations;// List of job invocation ids
            public int jobInterimStatus; // Interim status of job
            public Pointer jobInvocationId;// Job invocation id
            public Pointer jobDesc;
            public Pointer stageList2;
            // public Pointer jobElapsed;
            public int jobElapsed;
            public int jobDMIService; // Job is a DMI (aka WEB) service
            public int jobMultiInvokable;// Job can be multiply invoked
            public Pointer jobFullDesc; // Full job description
            public int jobRestartable; // Job is restartable
        }
    }
*/
    public static class JOBINFO extends Union {
        //public static class JOBINFO extends Structure {
        public int jobStatus; // Current status of job
        public Pointer jobController;// Name of controlling job
        //public time_t jobStartTime; // Date and time when job started
        public NativeLong jobStartTime;
        public int jobWaveNumber;// Wave number of current or last run
        public Pointer userStatus;// Last set user status
        public Pointer stageList; // List of stage names in job
        public Pointer paramList; // List of job parameter names
        public Pointer jobName; // Name of this job
        public int jobControl; // Current job control status
        public int jobPid; // Job process id
        //public time_t jobLastTime; // Date and time when job finished
        public NativeLong jobLastTime;
        public Pointer jobInvocations;// List of job invocation ids
        public int jobInterimStatus; // Interim status of job
        public Pointer jobInvocationId;// Job invocation id
        public Pointer jobDesc;
        public Pointer stageList2;
        // public Pointer jobElapsed;
        public int jobElapsed;
        public int jobDMIService; // Job is a DMI (aka WEB) service
        public int jobMultiInvokable;// Job can be multiply invoked
        public Pointer jobFullDesc; // Full job description
        public int jobRestartable; // Job is restartable
    }

    public static class DSLOGDETAIL extends Structure {
        public int eventId; // Id of this event
        public time_t timestamp;// Data and time event occurred
        public int type; // Type of event
        public Pointer username; // User name associated with event
        public Pointer fullMessage;// Full description of event
    }

    public static class DSLOGDETAILFULL extends Structure {
        public int eventId; // Id of this event
        public time_t timestamp; // Data and time event occurred
        public int type; // Type of event
        public Pointer username; // User name associated with event
        public Pointer fullMessage; // Full description of event
        public Pointer messageId; // Message Id
        public Pointer invocationId; // Invocation Id, if any
    }

    public static class DSLOGEVENT extends Structure {
        public int eventId; // Id of this event
        public time_t timestamp; // Data and time event occurred
        public int type; // Type of event
        public Pointer message; // Short event message
    }

    public static class DSLINKINFO extends Structure {
        public int infoType;
        public LINKINFO linkinfo/* = new LINKINFO() */; // Information value
    }

    public static class LINKINFO extends Union {
        public DSLOGDETAIL lastError; // Last error message from link
        public int rowCount; // number of rows that have passed down link
        public Pointer linkName; // Name of this link
        public Pointer linkSQLState;// SQL state for last error
        public Pointer linkDBMSCode; // DBMC code for last error
        public Pointer linkDesc;
        public Pointer linkeDS;
        public Pointer rowCountList;
    }

    public static class DSPARAM extends Structure {
        public int paramType; // Type of parameter
        public PARAM paramValue /* = new PARAM() */; // Parameter value
    }

    public static class PARAM extends Union {
        public Pointer pString; // String type
        public Pointer pEncrypt;// Encrypted string
        public int pInt; // Integer
        public float pFloat; // Float
        public Pointer pPath; // Pathname
        public Pointer pListValue; // String from a list
        public Pointer pDate; // Date string
        public Pointer pTime; // Time string
    }

    public static class DSPARAMINFO extends Structure {
        public DSPARAM defaultValue;// Default value of parameter
        public Pointer helpText; // Description of parameter
        public Pointer paramPrompt;// Prompt for the parameter
        public int paramType; // Type of parameter
        public DSPARAM desDefaultValue;
        public Pointer listValues; // Set of valid strings for List type
        public Pointer desListValues;
        // * Set of designer set valid strings for list type parameter
        public int promptAtRun; // Flag indicating prompt required at run time.
    }

    public static class DSPROJECTINFO extends Structure {
        public int infoType; // Key indicating type of information
        public PROJECTINFO info;/* = new PROJECTINFO(); */// Information //
        // value
    }

    public static class PROJECTINFO extends Union {
        public Pointer jobList; // List of names of all jobs in the project
        public Pointer projectName;// Name of the current project
        public Pointer projectPath;// Directory path of the current project
        public Pointer hostName; // Host name of the server
        public Pointer installTag; // Install tag of current server engine
        public Pointer tcpPort; // TCP port of current server engine
    }

    /*
     * public class DSREPOSJOBINFO extends Structure { public Pointer jobname;//
     * Includes category public int jobtype; // InfoType constant public
     * DSREPOSJOBINFO nextjob = new DSREPOSJOBINFO(); //public static class
     * ByValue extends DSREPOSJOBINFO implements Structure.ByValue{} //public
     * static class ByReference extends DSREPOSJOBINFO implements
     * Structure.ByReference{} }
     */
    public class _DSREPOSJOBINFO extends Structure {
        public Pointer jobname;// Includes category
        public int jobtype; // InfoType constant
        public DSREPOSJOBINFO.ByReference nextjob /* = new DSREPOSJOBINFO() */;
        // private boolean autoRead=false;
    }

    public class DSREPOSJOBINFO extends _DSREPOSJOBINFO {

        public static class ByValue extends DSREPOSJOBINFO implements
                Structure.ByValue {
        } //

        public static class ByReference extends DSREPOSJOBINFO implements
                Structure.ByReference {
        }

    }

    public class DSREPOSINFO extends Structure {
        public int infoType;
        public REPOSINFO info/*  = new REPOSINFO()*/ ;
    }

    public class REPOSINFO extends Union {
        // public DSREPOSJOBINFO.ByReference jobs /* = new DSREPOSJOBINFO() */;
        public DSREPOSJOBINFO.ByReference jobs;
        // public LinkedList<DSREPOSJOBINFO> jobs;
        // public LinkedHashMap<String,DSREPOSJOBINFO> jobs;
    }

    public static class DSREPOSUSAGEJOB extends Structure {
        public Pointer jobname; // Job and cat name
        public int jobtype; // type of job
        public DSREPOSUSAGEJOB.ByReference nextjob/* = new DSREPOSUSAGEJOB() */;
        // next job in linked list of jobs at same level
        public DSREPOSUSAGEJOB.ByReference childjob/* = new DSREPOSUSAGEJOB() */;

        public static class ByReference extends DSREPOSUSAGEJOB implements
                Structure.ByReference {
        }

        public static class ByValue extends DSREPOSUSAGEJOB implements
                Structure.ByValue {
        }
    }

    public static class DSREPOSUSAGE extends Structure {
        public int infoType;
        // public DSREPOSUSAGEJOB jobs/* = new DSREPOSUSAGEJOB() */;
        public REPOSUSAGE info;
        // linked list of jobs
    }

    public static class REPOSUSAGE extends Union {
        public DSREPOSUSAGEJOB.ByReference jobs;
    }

    public static class DSSTAGEINFO extends Structure {
        public int infoType; // Key indicating type of information
        public STAGEINFO info /* = new STAGEINFO() */; // Information value
    }

    public static class STAGEINFO extends Union {
        public DSLOGDETAIL lastError;// Last error message (log) from a link
        public Pointer typeName;// Name of stage type
        public int inRowNum; // Primary links input row number
        public Pointer linkList; // List of stage link names
        public Pointer stageName; // Name of current stage
        public Pointer varList; // List of stage variable names
        public time_t stageStartTime; // Date and time when stage started
        public time_t stageEndTime; // Date and time when stage finished
        public Pointer linkTypes;
        public Pointer stageDesc;
        public Pointer instList;
        public Pointer cpuList;
        public Pointer stageElapsed;
        public Pointer pidList;
        public int stageStatus;
        public Pointer custInfoList; // List of custom info names
    }

    public static class DSVARINFO extends Structure {
        public int infoType; // Key indicating type of information
        public VARINFO info /* = new VARINFO() */; // Information value
    }

    public static class VARINFO extends Union {
        public Pointer varValue;
        public Pointer varDesc;
    }

    public static class DSCUSTINFO extends Structure {
        public int infoType;
        // Key indicating type of information
        public CUSTINFO info;
        // Information value
    }

    public static class CUSTINFO extends Union {
        public Pointer custInfoValue;
        public Pointer custInfoDesc;
    }

    public static class DSREPORTINFO extends Structure {
        public int reportType;
        public REPORTINFO info/* = new REPORTINFO() */;
    }

    public static class REPORTINFO extends Union {
        public Pointer reportText;
    }

    public static class DSPROJECT extends Structure {
        public int dsapiVersionNo;
        // Version of DSAPI being used
        public int sessionId;
        // The InterCALL session id
        public byte valueMark;
        // This sessions value mark character
        public byte fieldMark;
        // This sessions field mark character
    }

    public static class DSJOB extends Structure {
        public DSPROJECT hProject;// = new DSPROJECT.ByReference();
        public Pointer serverJobHandle; // Text of handle to job on server
        public Pointer logData; // Cached log summary data
        public int logDataLen;// Size of log summary data
        public int logDataPsn; // Current position in logData
    }

    libvmdsapi INSTANCE = (libvmdsapi) Native.loadLibrary("vmdsapi",libvmdsapi.class);

    public Pointer DSGetProjectList();

    public void DSSetServerParams(String domain, String username,
                                  String password, String server);

    public int DSCloseJob(DSJOB JobHandle);

    public int DSCloseProject(DSPROJECT hProject);

    public int DSFindFirstLogEntry(DSJOB JobHandle, int EventType,
                                   time_t StartTime, time_t EndTime, int MaxNumber, DSLOGEVENT Event);

    public int DSFindNextLogEntry(DSJOB JobHandle, DSLOGEVENT Event);

    public int DSGetJobInfo(DSJOB JobHandle, int InfoType, DSJOBINFO ReturnInfo);
    //public int DSGetJobInfo1(DSJOB JobHandle, int InfoType, DSJOBINFO1 ReturnInfo);
    public int DSGetLastError();

    public Pointer DSGetLastErrorMsg(DSPROJECT hproject);

    public int DSGetLinkInfo(DSJOB JobHandle, String StageName,
                             String LinkName, int InfoType, DSLINKINFO ReturnInfo);

    public int DSGetVarInfo(DSJOB JobHandle, String StageName, String VarName,
                            int InfoType, DSVARINFO ReturnInfo);

    public int DSGetCustInfo(DSJOB JobHandle, String StageName,
                             String CustInfoname, int InfoType, DSCUSTINFO ReturnInfo);

    public int DSGetLogEntry(DSJOB JobHandle, int EventId, DSLOGDETAIL Event);

    public int DSGetNewestLogId(DSJOB JobHandle, int EventType);

    public int DSGetParamInfo(DSJOB JobHandle, String ParamName,
                              DSPARAMINFO ReturnInfo);

    public int DSGetProjectInfo(DSPROJECT hproject, int infoType,
                                DSPROJECTINFO ReturnInfo);

    public int DSGetReposInfo(DSPROJECT hProject, int ObjectType, int InfoType,
                              String SearchCriteria, String StartingCategory, int Subcategories,
                              DSREPOSINFO ReturnInfo);

    public int DSGetReposUsage(DSPROJECT hProject, int RelationshipType,
                               String ObjectName, int Recursive, DSREPOSUSAGE ReturnInfo);

    public int DSGetStageInfo(DSJOB JobHandle, String StageName, int InfoType,
                              DSSTAGEINFO ReturnInfo);

    public int DSLockJob(DSJOB JobHandle);

    public int DSLogEvent(DSJOB JobHandle, int EventType, String Reserved,
                          String Message);

    public DSJOB DSOpenJob(DSPROJECT hproject, String JobName);

    public DSPROJECT DSOpenProjectEx(int DSAPI_VERSION, String projectname);

    public int DSRunJob(DSJOB JobHandle, int flag);

    public int DSSetJobLimit(DSJOB JobHandle, int LimitType, int LimitValue);

    public int DSSetGenerateOpMetaData(DSJOB JobHandle, int value);

    public int DSSetParam(DSJOB JobHandle, String ParamName, DSPARAM Param);

    public int DSStopJob(DSJOB JobHandle);

    public int DSUnlockJob(DSJOB JobHandle);

    public int DSWaitForJob(DSJOB JobHandle);

    public int DSMakeJobReport(DSJOB JobHandle, int ReportType,
                               String LineSeparator, DSREPORTINFO ReturnInfo);

    // public Pointer DSGetIdForJob(DSPROJECT Hproject, String Jonname);

	/*
	 * Administration functions - nothing to do with job control - these are
	 * used by the dsadmin command line tool
	 */

    public int DSAddProject(String ProjectName, String ProjectLocation);

    public int DSDeleteProject(String ProjectName);

    public int DSSetProjectProperty(DSPROJECT hproject, String property,
                                    String Value);

    public int DSAddEnvVar(DSPROJECT hproject, String envvarname, String type,
                           String promptText, String value);

    public int DSSetEnvVar(DSPROJECT hProject, String EnvVarName, String Value);

    public int DSDeleteEnvVar(DSPROJECT hProject, String EnvVar);

    public Pointer DSListProjectProperties(DSPROJECT hProject);

    public Pointer DSListEnvVars(DSPROJECT hProject);

    public int DSGetLogEventIds(DSJOB JobHandle, int RunNumber, String Filter,
                                PointerByReference List);
}
