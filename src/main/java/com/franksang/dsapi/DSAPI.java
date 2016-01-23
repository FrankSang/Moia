package com.franksang.dsapi;

/**
 * Created by Administrator on 2015/9/29.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.franksang.dsapi.exception.*;
import com.franksang.dsapi.properties.prop;
import com.franksang.dsapi.vmdsapi.libvmdsapi;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DSAPI {

    private static Logger logger = LoggerFactory.getLogger(DSAPI.class);

    //需要同时链接多个工程，所以次变量不能设置成静态变量
    private libvmdsapi session = null;
    /**
     * ProjectHandle 工程句柄
     */
    private libvmdsapi.DSPROJECT ProjectHandle = null;
    /**
     * DSJS map job状态码和描述的映射关系
     */
    private static Map<Integer, String> DSJS = new HashMap<Integer, String>();
    /**
     * ProjectName 工程名称
     */
    private String ProjectName = null;


    /**
     * DSPROJECT getProjectHandle()
     * void setProjectHandle(DSPROJECT projectHandle)
     */
    public libvmdsapi.DSPROJECT getProjectHandle() {
        return ProjectHandle;
    }

    public void setProjectHandle(libvmdsapi.DSPROJECT projectHandle) {
        ProjectHandle = projectHandle;
    }

    public libvmdsapi getSession() {
        return session;
    }

    public void setSession(libvmdsapi session) {
        this.session = session;
    }

    /**
     * public DSAPI(String ProjectName)
     * 当该程序部署在DataStage Engine 服务器上时，不需要输入登陆信息，就可以直接打开工程
     */
    public DSAPI(String ProjectName) {

        libvmdsapi lib = libvmdsapi.INSTANCE;
        //this.setProjectName(ProjectName);
        this.ProjectName = ProjectName;
        this.setSession(lib);
        if (!(this.DSGetProjectlist().contains(ProjectName))) {
            throw new ProjectNoFoundException("Project:" + ProjectName + " not exists ");
        }
        this.setProjectHandle(this.DSOpenProject(ProjectName));
    }

    /**
     * DSAPI(String domain, String username, String password, String server, String ProjectName)
     * 当该程序部署在与DataStage Engine 分开的服务器上时，需要输入登陆信息，才可以直接打开工程
     */
    public DSAPI(String domain, String username, String password, String server, String ProjectName) {
        libvmdsapi lib = libvmdsapi.INSTANCE;
        this.ProjectName = ProjectName;
        lib.DSSetServerParams(":" + domain, username, password, server);
        this.setSession(lib);
        if (!(this.DSGetProjectlist().contains(ProjectName))) {
            throw new ProjectNoFoundException("Project:" + ProjectName + " not exists in domain " + domain + " and server " + server);
        }
        this.setProjectHandle(this.DSOpenProject(ProjectName));
    }

    //一些全局变量的设置
    public static final int DSJ_LOGINFO = 1;	/* Information message. */
    public static final int DSJ_LOGWARNING = 2; /* Warning message. */
    public static final int DSJ_LOGFATAL = 3; /* Fatal error message. */
    public static final int DSJ_LOGREJECT = 4; /* Rejected row message. */
    public static final int DSJ_LOGSTARTED = 5; /* Job started message. */
    public static final int DSJ_LOGRESET = 6; /* Job reset message. */
    public static final int DSJ_LOGBATCH = 7; /* Batch control */
    public static final int DSJ_LOGOTHER = 98;	/* Category other than above */
    public static final int DSJ_LOGANY = 99;	/* Any type of event */
    public static final int DSAPI_VERSION = 1;
    public static final int DSJ_RUNNORMAL = 1;   // Standard job run.
    public static final int DSJ_RUNRESET = 2;    // Job is to be reset.
    public static final int DSJ_RUNVALIDATE = 3;    // Job is to be validated only.
    // Restart job with previous parameters, job must be in Restartable state.
    public static final int DSJ_RUNRESTART = 4;
    public static final int DSJ_REPORT0 = 0;
    public static final int DSJ_REPORT1 = 1;
    public static final int DSJ_REPORT2 = 2;

    // public static final String DSA_ENVVAR_TYPE_STRING = "String";
    // public static final String DSA_ENVVAR_TYPE_ENCRYPTED = "Encrypted";
    public static final int DSJE_NOTADMINUSER = -100;    // User is not an administrative user
    public static final int DSJE_ISADMINFAILED = -101;    // Unable to determine if user is an administrative user
    public static final int DSJE_READPROJPROPERTY = -102;    // Reading project properties failed
    public static final int DSJE_WRITEPROJPROPERTY = -103;    // Writing project properties failed
    public static final int DSJE_BADPROPERTY = -104;    // Property name is invalid
    public static final int DSJE_PROPNOTSUPPORTED = -105;    // Unsupported property
    public static final int DSJE_BADPROPVALUE = -106;    // Value given is not valid for this property
    public static final int DSJE_OSHVISIBLEFLAG = -107;    // Failed to set OSHVisible value
    public static final int DSJE_BADENVVARNAME = -108;    // Invalid environment variable name
    public static final int DSJE_BADENVVARTYPE = -109;    // Invalid environment variable type
    public static final int DSJE_BADENVVARPROMPT = -110;    // Missing environment variable prompt
    public static final int DSJE_READENVVARDEFNS = -111;    // Reading environment variable definitions failed
    public static final int DSJE_READENVVARVALUES = -112;    // Reading environment variable values failed
    public static final int DSJE_WRITEENVVARDEFNS = -113;    // Writing environment variable definitions failed
    public static final int DSJE_WRITEENVVARVALUES = -114;    // Writing environment variable values failed
    public static final int DSJE_DUPENVVARNAME = -115;    // Environment variable name already exists
    public static final int DSJE_BADENVVAR = -116;    // Environment variable name not recognised
    public static final int DSJE_NOTUSERDEFINED = -117;    // Environment variable is not user defined
    public static final int DSJE_BADBOOLEANVALUE = -118;    // Invalid value given for a boolean environment variable
    public static final int DSJE_BADNUMERICVALUE = -119;    // Invalid value given for a numeric environment variable
    public static final int DSJE_BADLISTVALUE = -120;    // Invalid value given for a list environment variable
    public static final int DSJE_PXNOTINSTALLED = -121;    // PX not installed
    public static final int DSJE_ISPARALLELLICENCED = -122;    // Failed to find out if PX licensed
    public static final int DSJE_ENCODEFAILED = -123;    // Encoding of an encrypted value failed
    public static final int DSJE_DELPROJFAILED = -124;    // Deletion of project definition & SCHEMA failed
    public static final int DSJE_DELPROJFILESFAILED = -125;    // Deletion of project files & subdirectories failed
    public static final int DSJE_LISTSCHEDULEFAILED = -126;    // Failed to get list of scheduled jobs for project
    public static final int DSJE_CLEARSCHEDULEFAILED = -127;    // Failed to clear scheduled jobs for project
    public static final int DSJE_BADPROJNAME = -128;    // Project name contains invalid characters
    public static final int DSJE_GETDEFAULTPATHFAILED = -129;    // Failed to get default path for project
    public static final int DSJE_BADPROJLOCATION = -130;    // Project location path contains invalid characters
    public static final int DSJE_INVALIDPROJECTLOCATION = -131;    // Project location is invalid
    public static final int DSJE_OPENFAILED = -132;    // Failed to open file
    public static final int DSJE_READUFAILED = -133;    // Failed to lock administration record
    public static final int DSJE_ADDPROJECTBLOCKED = -134;    // Administration record locked by another user
    public static final int DSJE_ADDPROJECTFAILED = -135;    // Adding project failed
    public static final int DSJE_LICENSEPROJECTFAILED = -136;     // Licensing project failed
    public static final int DSJE_RELEASEFAILED = -137;    // Failed to release administration record
    public static final int DSJE_DELETEPROJECTBLOCKED = -138;    // Project locked by another user
    public static final int DSJE_NOTAPROJECT = -139;    // Failed to log to project
    public static final int DSJE_ACCOUNTPATHFAILED = -140;    // Failed to get account path
    public static final int DSJE_LOGTOFAILED = -141;    // Failed to log to UV account
    public static final int DSJE_PROTECTFAILED = -142;
    public static final int DSJE_NOERROR = 0;    // no errors
    public static final int DSJE_BADHANDLE = -1;    // Invalid JobHandle.
    public static final int DSJE_BADSTATE = -2;    // Job is not in the right state (must be compiled & not running).
    public static final int DSJE_BADPARAM = -3;    // ParamName is not a parameter name in the job.
    public static final int DSJE_BADVALUE = -4;    // LimitValue is not appropriate for the limiting condition type.
    public static final int DSJE_BADTYPE = -5;    // Invalid EventType value
    public static final int DSJE_WRONGJOB = -6;    // Job for this JobHandle was not started from a call to DSRunJob by the
    // current process.
    public static final int DSJE_BADSTAGE = -7;    // StageName does not refer to a known stage in the job.
    public static final int DSJE_NOTINSTAGE = -8;    // INTERNAL TO SERVER
    public static final int DSJE_BADLINK = -9;    // LinkName does not refer to a known link for the stage in question.
    public static final int DSJE_JOBLOCKED = -10;    // Job is locked by another user
    public static final int DSJE_JOBDELETED = -11;    // Job has been deleted !
    public static final int DSJE_BADNAME = -12;    // Job name badly formed
    public static final int DSJE_BADTIME = -13;    // Timestamp parameter was badly formed
    public static final int DSJE_TIMEOUT = -14;    // Given up waiting for something to happen
    public static final int DSJE_DECRYPTERR = -15;    // Decryption of encrypted value failed
    public static final int DSJE_NOACCESS = -16;    // Cannot get values, Default values job except the current job (Job Handle
    // == DSJ.ME)
    public static final int DSJE_NOTEMPLATE = -17;    // Cannot find template file
    public static final int DSJE_BADTEMPLATE = -18;    // Error encountered when processing template file
    public static final int DSJE_NOPARAM = -19;    // Parameter name missing=- field does not look like 'name:value'
    public static final int DSJE_NOFILEPATH = -20;    // File path name not given
    public static final int DSJE_CMDERROR = -21;    // Error when executing external command
    public static final int DSJE_BADVAR = -22;    // Stage Variable name not recognised.
    public static final int DSJE_NONUNIQUEID = -23;    // Id already exists for a job in this project.
    public static final int DSJE_INVALIDID = -24;    // Invalid Job Id
    public static final int DSJE_INVALIDQUEUE = -25;    // Invalid Queue
    public static final int DSJE_WLMDISABLED = -26;    // WLM is not enabled
    public static final int DSJE_WLMNOTRUNNING = -27;    // WLM is not running
    public static final int DSJE_REPERROR = -99;    // General server 'other error'
    // Protect or unprotect project failed
    public static final int DSJE_NOMORE = -1001;    // All events matching the filter criteria have been returned.
    public static final int DSJE_BADPROJECT = -1002;    // Unknown project name
    public static final int DSJE_NO_DATASTAGE = -1003;    // DataStage not installed on server
    public static final int DSJE_OPENFAIL = -1004;    // Attempt to open job failed
    public static final int DSJE_NO_MEMORY = -1005;    // Malloc failure
    public static final int DSJE_SERVER_ERROR = -1006;    // Server generated error error msg text desribes it
    public static final int DSJE_NOT_AVAILABLE = -1007;    // Not data available from server
    public static final int DSJE_BAD_VERSION = -1008;    // Version is DSOpenProjectEx is invalid
    public static final int DSJE_INCOMPATIBLE_SERVER = -1009;    // Server version incompatible with this version of the DSAPI
    public static final int DSJE_DOMAINLOGTOFAILED = -1010;    // Failed to authenticate to Domain
    public static final int DSJE_NOPRIVILEGE = -1011;    // The isf user does not have sufficient DataStage privileges

    public static final int DSJ_JOBSTATUS = 1;// Current status of the job.
    public static final int DSJ_JOBNAME = 2;
    // Name of the job referenced by JobHandle.
    public static final int DSJ_JOBCONTROLLER = 3;
    // Name of job controlling the job referenced by JobHandle.
    public static final int DSJ_JOBSTARTTIMESTAMP = 4;
    // Date and time when the job started.
    public static final int DSJ_JOBWAVENO = 5;
    // Wave number of last or current run.
    public static final int DSJ_PARAMLIST = 6;
    // List of job parameter names
    public static final int DSJ_STAGELIST = 7;
    // List of names of stages in job
    public static final int DSJ_USERSTATUS = 8;
    // Value, if any, set as the user status by the job.
    public static final int DSJ_JOBCONTROL = 9;
    // Job control STOP/SUSPEND/RESUME
    public static final int DSJ_JOBPID = 10;
    // Process id of DSD.RUN process
    public static final int DSJ_JOBLASTTIMESTAMP = 11;
    // Server date/time of job last finished: "YYYY-MM-DD HH:MM:SS"
    public static final int DSJ_JOBINVOCATIONS = 12;
    // Comma-separated list of job invocation ids
    public static final int DSJ_JOBINTERIMSTATUS = 13;
    // Current interim status of job
    public static final int DSJ_JOBINVOCATIONID = 14;
    // Invocation name of the job referenced
    public static final int DSJ_JOBDESC = 15;
    // Job description
    public static final int DSJ_STAGELIST2 = 16;
    // list of stages not in DSJ.STAGELIST
    public static final int DSJ_JOBELAPSED = 17;
    // Job Elapsed time in seconds
    public static final int DSJ_JOBEOTCOUNT = 18;
    public static final int DSJ_JOBEOTTIMESTAMP = 19;
    public static final int DSJ_JOBDMISERVICE = 20;
    // Job is a DMI (aka WEB) service
    public static final int DSJ_JOBMULTIINVOKABLE = 21;
    // Job can be multiply invoked
    public static final int DSJ_JOBFULLDESC = 22;
    // Full job description
    public static final int DSJ_JOBRESTARTABLE = 24;
    // Job can be restarted
    public static final int DSJS_RUNNING = 0;
    // Job running
    public static final int DSJS_RUNOK = 1;
    // Job finished a normal run with no warnings
    public static final int DSJS_RUNWARN = 2;
    // Job finished a normal run with warnings
    public static final int DSJS_RUNFAILED = 3;
    // Job finished a normal run with a fatal error
    public static final int DSJS_QUEUED = 4;
    // Job queued waiting for resource allocation
    public static final int DSJS_VALOK = 11;
    // Job finished a validation run with no warnings
    public static final int DSJS_VALWARN = 12;
    // Job finished a validation run with warnings
    public static final int DSJS_VALFAILED = 13;
    // Job failed a validation run
    public static final int DSJS_RESET = 21;
    // Job finished a reset run
    public static final int DSJS_CRASHED = 96;
    // Job was stopped by some indeterminate action
    public static final int DSJS_STOPPED = 97;
    // Job was stopped by operator intervention (can't tell run type)
    public static final int DSJS_NOTRUNNABLE = 98;
    // Job has not been compiled
    public static final int DSJS_NOTRUNNING = 99;
    // Any other status
    public static final int DSS_JOBS = 1;
    // The Object Type to return
    public static final int DSS_JOB_ALL = 15;
    // list all jobs
    public static final int DSS_JOB_SERVER = 1;
    // list all server jobs
    public static final int DSS_JOB_PARALLEL = 2;
    // list all parallel jobs
    public static final int DSS_JOB_MAINFRAME = 4;
    // list all mainframe jobs
    public static final int DSS_JOB_SEQUENCE = 8;
    // list all sequence jobs
    public static final int DSS_JOB_USES_JOB = 1;
    public static final int DSS_JOB_USEDBY_JOB = 2;
    public static final int DSS_JOB_HASSOURCE_TABLEDEF = 3;
    public static final int DSS_JOB_HASTARGET_TABLEDEF = 4;
    public static final int DSS_JOB_HASSOURCEORTARGET_TABLEDEF = 5;

    /**
     * 设置job状态可状态编码的mapping
     * */
    static {
        //Map<Integer, String> DSJS = new HashMap<Integer, String>();
        DSJS.put(0, "DSJS_RUNNING");
        DSJS.put(1, "DSJS_RUNOK");
        DSJS.put(2, "DSJS_RUNWARN");
        DSJS.put(3, "DSJS_RUNFAILED");
        DSJS.put(4, "DSJS_QUEUED");
        DSJS.put(11, "DSJS_VALOK");
        DSJS.put(12, "DSJS_VALWARN");
        DSJS.put(13, "DSJS_VALFAILED");
        DSJS.put(21, "DSJS_RESET");
        DSJS.put(96, "DSJS_CRASHED");
        DSJS.put(97, "DSJS_STOPPED");
        DSJS.put(98, "DSJS_NOTRUNNABLE");
        DSJS.put(99, "DSJS_NOTRUNNING");
        //System.out.println(DSJS);
    }

    /**
     * int DSCloseProject()
     * 关闭工程，没有返回码
     */
    public int DSCloseProject() {
        //int returnnum =
        this.getSession().DSCloseProject(ProjectHandle);
        return DSJE_NOERROR;
    }

    /**
     *关闭job
     * 参数信息
     * JobHandle 被打开的JOB
     * */

    public void DSCloseJob(libvmdsapi.DSJOB JobHandle) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSCloseJob(JobHandle);
        if (returnnum != 0) {
            logger.error("DSUlockJob:DSJE_BADHANDLE Invalid JobHandle.");
        }
    }

    /**
     * 增加工程级别的环境变量
     * 参数信息：
     * EvnVarame :变量名称
     * Type      :变量类型
     * PromptText:变量的提示信息
     * Value     :变量值
     * */

    public void DSAddEnvVar(String EnvVarName, String Type, String PromptText, String Value) {
        int returnnum = this.getSession().DSAddEnvVar(ProjectHandle, EnvVarName, Type, PromptText,
                Value);
        if (returnnum != 0) {
            switch (returnnum) {
                case DSJE_BADENVVARNAME:
                    logger.error("invalid environment variable name");
                    break;
                case DSJE_BADENVVARTYPE:
                    logger.error("invalid environment variable type");
                    break;
                case DSJE_BADENVVARPROMPT:
                    logger.error("Missing environment variable prompt");
                    break;
                case DSJE_READENVVARDEFNS:
                    logger.error("Reading environment variable definitions failed");
                    break;
                case DSJE_READENVVARVALUES:
                    logger.error("Reading environment variable values failed");
                    break;
                case DSJE_WRITEENVVARDEFNS:
                    logger.error(" Writing environment variable definitions failed");
                    break;
                case DSJE_WRITEENVVARVALUES:
                    logger.error("Writing environment variable values failed");
                    break;
                case DSJE_DUPENVVARNAME:
                    logger.error("Environment variable name already exists ");
                    break;
                case DSJE_ENCODEFAILED:
                    logger.error("Encoding of an encrypted value failed");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *增加工程，该方法暂时只有在程序部署在Datatage Engine服务器上是才可以使用
     * 参数信息:
     * ProjectName:工程名称
     * ProjectLocation:工程目录(包含工程名称的目录)
     *
     * */
    public void DSAddProject(String ProjectName,
                             String ProjectLocation) {
        int returnnum = this.getSession().DSAddProject(ProjectName, ProjectLocation);
        if (returnnum != 0) {
            switch (returnnum) {
                case DSJE_NOTADMINUSER:
                    logger.error("User is not an administrative user");
                    break;
                case DSJE_ISADMINFAILED:
                    logger.error("Unable to determine if user is an administrative user");
                    break;
                case DSJE_BADPROJNAME:
                    logger.error("Project name contains invalid characters");
                    break;
                case DSJE_GETDEFAULTPATHFAILED:
                    logger.error("Failed to get default path for project");
                    break;
                case DSJE_BADPROJLOCATION:
                    logger.error("Project location path contains invalid characters");
                    break;
                case DSJE_INVALIDPROJECTLOCATION:
                    logger.error("Project location is invalid");
                    break;
                case DSJE_OPENFAILED:
                    logger.error("Failed to open file");
                    break;
                case DSJE_READUFAILED:
                    logger.error("Failed to lock administration record");
                    break;
                case DSJE_ADDPROJECTBLOCKED:
                    logger.error("Administration record locked by another user");
                    break;
                case DSJE_LICENSEPROJECTFAILED:
                    logger.error("Licensing project failed");
                    break;
                case DSJE_ADDPROJECTFAILED:
                    logger.error("Adding project failed");
                    break;
                case DSJE_RELEASEFAILED:
                    logger.error("Failed to release administration record");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 删除变量
     * 参数信息
     * EnvVar:变量的名称
     * */
    public void DSDeleteEnvVar(String EnvVar) {
        int returnnum = this.getSession().DSDeleteEnvVar(ProjectHandle, EnvVar);
        switch (returnnum) {
            case DSJE_NOERROR:
                break;
            case DSJE_READENVVARDEFNS:
                logger.error("Reading environment variable definitions failed");
                break;
            case DSJE_BADENVVAR:
                logger.error("Missing environment variable prompt");
                break;
            case DSJE_WRITEENVVARDEFNS:
                logger.error(" Writing environment variable definitions failed");
                break;
            case DSJE_WRITEENVVARVALUES:
                logger.error("Writing environment variable values failed");
                break;
            case DSJE_NOTUSERDEFINED:
                logger.error("Environment variable is not user defined");
                break;
            default:
                break;
        }
    }

    /**
     *删除工程
     * 该方法只有在程序部署在Datatage Engine服务器上时才可以使用
     * 参数信息:
     * ProjectName: 工程名称
     * */
    public void DSDeleteProject(String ProjectName) {
        int returnnum = this.getSession().DSDeleteProject(ProjectName);
        switch (returnnum) {
            case DSJE_NOERROR:
                System.out.println("delete project " + ProjectName + "Successfull");
                break;
            case DSJE_NOTADMINUSER:
                logger.error("user is not an administrator");
                break;
            case DSJE_ISADMINFAILED:
                logger.error("failed to determine whether user is an administrator");
                break;
            case DSJE_OPENFAILED:
                logger.error("failed to open UV.ACCOUNT file");
                break;
            case DSJE_READUFAILED:
                logger.error("failed to lock project record");
                break;
            case DSJE_RELEASEFAILED:
                logger.error("failed to release project record");
                break;
            case DSJE_LISTSCHEDULEFAILED:
                logger.error("failed to get list of scheduled jobs for project");
                break;
            case DSJE_CLEARSCHEDULEFAILED:
                logger.error("failed to clear scheduled jobs for project");
                break;
            case DSJE_DELETEPROJECTBLOCKED:
                logger.error("project locked by another user");
                break;
            case DSJE_NOTAPROJECT:
                logger.error("failed to log to project");
                break;
            case DSJE_ACCOUNTPATHFAILED:
                logger.error("failed to get account path");
                break;
            case DSJE_LOGTOFAILED:
                logger.error("failed to log to UV account");
                break;
            case DSJE_DELPROJFAILED:
                logger.error("failed to delete project definition");
                break;
            case DSJE_DELPROJFILESFAILED:
                logger.error("failed to delete project files");
                break;
            default:
                break;
        }
    }

    /**
     *
     * */
    public void DSFindFirstLogEntry(libvmdsapi.DSJOB JobHandle,
                                    int EventType, libvmdsapi.time_t StartTime, libvmdsapi.time_t EndTime, int MaxNumber,
                                    libvmdsapi.DSLOGEVENT Event) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSFindFirstLogEntry(JobHandle, EventType,
                StartTime, EndTime, MaxNumber, Event);
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSFindFirstLogEntry Sucessfully");
                //System.out.println();
                break;
            case DSJE_NOMORE:
                logger.error("There are no events matching the filter criteria.");
                break;
            case DSJE_NO_MEMORY:
                logger.error("Failed to allocate memory for results from engine.");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid EventType value.");
                break;
            case DSJE_BADTIME:
                logger.error("Invalid StartTime or EndTime value.");
                break;
            case DSJE_BADVALUE:
                logger.error("Invalid MaxNumber value.");
                break;
            default:
                break;
        }
    }

    /**
     *
     * */
    public void DSFindNextLogEntry(libvmdsapi.DSJOB JobHandle,
                                   libvmdsapi.DSLOGEVENT Event) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }

        int returnnum = this.getSession().DSFindNextLogEntry(JobHandle,
                Event);
        switch (returnnum) {
           case DSJE_NOERROR:
               logger.info("DSFindNextLogEntry:Successfully");
                //System.out.println("DSFindNextLogEntry:Successfully");
                break;
            case DSJE_NOMORE:
                logger.error("All events matching the filter criteria have been returned");
                break;
            case DSJE_SERVER_ERROR:
                logger.error("Internal error. The engine returned invalid data.");
                break;
            default:
                break;
        }
    }

    /**
     *
     * */
    public void DSGetCustInfo(libvmdsapi.DSJOB JobHandle,
                              String StageName, String CustinfoName, int InfoType,
                              libvmdsapi.DSCUSTINFO ReturnInfo) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetCustInfo(JobHandle, StageName, CustinfoName,
                InfoType, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSGetCustInfo:Successful");
                //System.out.println("DSGetCustInfo:Successful");
                break;
            case DSJE_NOT_AVAILABLE:
                logger.error("There are no instances of the requested information in the stage.");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_BADSTAGE:
                logger.error("StageName does not refer to a known stage in the job.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid InfoType.");
                break;
            default:
                break;
        }
    }

    /**
     *
     * */
    public void DSGetJobInfo(libvmdsapi.DSJOB JobHandle, int InfoType,
                             libvmdsapi.DSJOBINFO ReturnInfo) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetJobInfo(JobHandle, InfoType, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSGetJobInfo:Successfull");
                 //System.out.println("DSGetJobInfo:Successfull");
                break;
            case DSJE_NOT_AVAILABLE:
                logger.error("There are no instances of the requested information in the job.");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid InfoType.");
                break;
            default:
                break;
        }
    }

    /*public void DSGetJobInfo1(DSJOB JobHandle, int InfoType,
            DSJOBINFO1 ReturnInfo) {
        int returnnum = lib.DSGetJobInfo1(JobHandle, InfoType, ReturnInfo);
        switch (returnnum) {
        case DSJE_NOERROR:
            // System.out.println("DSGetJobInfo:Successfull");
            break;
        case DSJE_NOT_AVAILABLE:
            logger.error("There are no instances of the requested information in the job.");
            break;
        case DSJE_BADHANDLE:
            logger.error("Invalid JobHandle.");
            break;
        case DSJE_BADTYPE:
            logger.error("Invalid InfoType.");
            break;
        default:
            break;
        }
    }
*/

    /**
     * libvmdsapi.DSGetLastError
     */
/*    public int DSGetLastError() {
        return 0;
    }*/

    /**
     *
     * */
    public String DSGetLastErrorMsg() {
        Pointer pointer = this.getSession().DSGetLastErrorMsg(ProjectHandle);
        return prop.GetPointer(pointer).replaceAll("  +", "");
    }

    /**
     *
     * */
    public void DSGetLinkInfo(libvmdsapi.DSJOB JobHandle,
                              String StageName, String LinkName, int InfoType,
                              libvmdsapi.DSLINKINFO ReturnInfo) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetLinkInfo(JobHandle, StageName, LinkName,
                InfoType, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                System.out.println("DSGetLinkInfo:Successfully");
                break;
            case DSJE_NOT_AVAILABLE:
                logger.error("There is no instance of the requested information available.");
                break;
            case DSJE_BADHANDLE:
                logger.error("JobHandle was invalid.");
                break;
            case DSJE_BADTYPE:
                logger.error("InfoType was unrecognized.");
                break;
            case DSJE_BADSTAGE:
                logger.error("StageName does not refer to a known stage in the job.");
                break;
            case DSJE_BADLINK:
                logger.error("LinkName does not refer to a known link for the stage in question");
                break;
            default:
                break;
        }
    }

    // FrangSang 2014-05-11测试成功

    /**
     *
     * */
    public void DSGetLogEntry(libvmdsapi.DSJOB JobHandle, int EventId,
                              libvmdsapi.DSLOGDETAIL Event) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetLogEntry(JobHandle, EventId, Event);
        switch (returnnum) {
            case DSJE_NOERROR:
                // System.out.println("DSGetLogEntry:Successfully");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_SERVER_ERROR:
                logger.error("Internal error. Engine returned invalid data.");
                break;
        /*
         * case DSJE_BADEVENTID:
		 * logger.error("Invalid event if for a specified job."); break;
		 */
            default:
                break;
        }
    }

    // FrangSang 2014-05-11测试成功

    /**
     *
     * */
    public int[] DSGetLogEventIds(libvmdsapi.DSJOB JobHandle,
                                  int RunNumber, String Filter, PointerByReference List) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession()
                .DSGetLogEventIds(JobHandle, RunNumber, Filter, List);
        this.getSession().DSGetLogEventIds(JobHandle, 0, "", List);
        String[] logids = List.getValue().getString(0).split("\\\\");
        int[] enevtids = new int[logids.length];
        switch (returnnum) {
            case DSJE_NOERROR:
                System.out.println("DSGetLogEventIds:Successfully");
                for (int i = 0; i < logids.length; i++) {
                    enevtids[i] = Integer.parseInt(logids[i]);
                }
                return enevtids;
            case DSJE_BADHANDLE:
                logger.error("Invalid job handle.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid Filter value.");
                break;
            case DSJE_BADVALUE:
                logger.error("Invalid RunNumber value.");
                break;
            default:
                break;
        }
        return enevtids;
    }

    // FrangSang 2014-05-11测试成功

    /**
     *
     * */
    public int[] DSGetLogEventIds(libvmdsapi.DSJOB JobHandle,
                                  int RunNumber, String Filter) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        PointerByReference List = new PointerByReference();
       /* int returnnum = this.getSession()*/
       /*         .DSGetLogEventIds(JobHandle, RunNumber, Filter, List);*/
        int returnnum = this.getSession().DSGetLogEventIds(JobHandle, 0, "", List);
        String[] logids = List.getValue().getString(0).split("\\\\");
        int[] enevtids = new int[logids.length];
        switch (returnnum) {
            case DSJE_NOERROR:
                // System.out.println("DSGetLogEventIds:Successfully");
                for (int i = 0; i < logids.length; i++) {
                    enevtids[i] = Integer.parseInt(logids[i]);
                }
                return enevtids;
            case DSJE_BADHANDLE:
                logger.error("Invalid job handle.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid Filter value.");
                break;
            case DSJE_BADVALUE:
                logger.error("Invalid RunNumber value.");
                break;
            default:
                break;
        }
        return enevtids;
    }

    /**
     *
     * */
    public int DSGetNewestLogId(libvmdsapi.DSJOB JobHandle, int EventType) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetNewestLogId(JobHandle, EventType);
        if (returnnum == -1) {
            logger.error("DSGetNewestLogId:Error");
            System.exit(1);
        }
        return returnnum;
    }

    /**
     *
     * */
    public void DSGetParamInfo(libvmdsapi.DSJOB JobHandle,
                               String ParamName, libvmdsapi.DSPARAMINFO ReturnInfo) {

        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }

        int returnnum = this.getSession().DSGetParamInfo(JobHandle, ParamName, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSGetParamInfo:Successfully");
                break;
            case DSJE_SERVER_ERROR:
                logger.error("Internal error. Engine returned invalid data.");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            default:
                break;
        }
    }

    /**
     *
     * */
    public void DSGetProjectInfo(int InfoType, libvmdsapi.DSPROJECTINFO ReturnInfo) {
        int returnnum = this.getSession().DSGetProjectInfo(ProjectHandle, InfoType, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSGetProjectInfo:Successfully");
                break;
            case DSJE_NOT_AVAILABLE:
                logger.error("There are no compiled jobs defined within the project.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid InfoType.");
                break;
            default:
                break;
            //return "";
        }
    }

    // public Pointer DSGetProjectList();
    //
    // FrankSang 2014-05-11

    /**
     *
     * */
    public HashSet<String> DSGetProjectlist() {
        HashSet<String> plist = new HashSet<String>();
        Pointer projectlist = this.getSession().DSGetProjectList();
        int longoffset = 0;
        String projectname = projectlist.getString(0);
        while (!projectname.isEmpty())
        {
            projectname = projectlist.getString(longoffset);
            longoffset = longoffset + projectname.length() + 1;
            if (!projectname.equals("")) {
                plist.add(projectname);
            }
        }
        return plist;
    }

    //
    // FrankSang 2014-05-11
    //

    /**
     *
     * */
 /*   public HashSet<String> DSGetJoblist(String projectname) {
        HashSet<String> JobList = new HashSet<String>();
        libvmdsapi.DSPROJECT projecthandle = this.DSOpenProject(projectname);

        int longoffset = 0;

        libvmdsapi.DSPROJECTINFO pinfo = new libvmdsapi.DSPROJECTINFO();
        this.getSession().DSGetProjectInfo(projecthandle, 1, pinfo);
        Pointer jobList = pinfo.info.jobList;
        String jobname = jobList.getString(0);
        while (!jobname.isEmpty()) {
            jobname = jobList.getString(longoffset);
            longoffset = longoffset + jobname.length() + 1;
            if (!jobname.equals("")) {
                JobList.add(jobname);
            }
            // JobList.add(jobname);
        }
        //this.getSession().DSCloseProject(projecthandle);
        return JobList;
    }*/

    // FrankSang 2014-05-11

    /**
     *
     * */
    public HashSet<String> DSGetJoblist() {
        HashSet<String> JobList = new HashSet<String>();
        // DSPROJECT projecthandle = this.DSOpenProject(lib, projectname);
        // ָ
        int longoffset = 0;
        //
        libvmdsapi.DSPROJECTINFO pinfo = new libvmdsapi.DSPROJECTINFO();
        // this.getSession().DSGetProjectInfo(ProjectHandle, 1, pinfo);
        this.DSGetProjectInfo(1, pinfo);
        Pointer jobList = pinfo.info.jobList;
        String jobname = jobList.getString(0);
        while (!jobname.isEmpty()) {
            jobname = jobList.getString(longoffset);
            longoffset = longoffset + jobname.length() + 1;
            if (!jobname.equals("")) {
                JobList.add(jobname);
            }
            // JobList.add(jobname);
        }
        //this.getSession().DSCloseProject(ProjectHandle);
        return JobList;
    }

    /**
     *
     * */
    public String DSGetReposInfo(int ObjectType, int InfoType, String SearchCriteria,
                                 String StartingCategory, int Subcategories, libvmdsapi.DSREPOSINFO ReturnInfo) {
        int returnnum = this.getSession().DSGetReposInfo(ProjectHandle, ObjectType, InfoType,
                SearchCriteria, StartingCategory, Subcategories, ReturnInfo);
        //System.out.println(returnnum);
        switch (returnnum) {
            case DSJE_BADTYPE:
                logger.error("ObjectType or InfoType values was not recognized");
                break;
            case DSJE_REPERROR:
                logger.error("An error occurred while trying to access the repository. Call"
                                + "DSGetLastErrorMsg to get the error message associated with the error code");
                break;
            case DSJE_NO_DATASTAGE:
                logger.error("The attached project does not appear to be a valid InfoSphere DataStage project");
                break;
            default:
                break;
        }
        if (returnnum == 0) {
            return null;
        }
        return prop.GetPointer(
                ((libvmdsapi.DSREPOSJOBINFO) ReturnInfo.info.readField("jobs")).jobname)
                .replaceAll("  +", "");
    }

    // 2014-05-21

    /**
     *
     * */
    public String DSGetReposUsage(int RelationshipType, String ObjectName, int Recursive,
                                  libvmdsapi.DSREPOSUSAGE ReturnInfo) {
        int returnum = this.getSession().DSGetReposUsage(ProjectHandle, RelationshipType,
                ObjectName, Recursive, ReturnInfo);
        //System.out.println(returnum);
        switch (returnum) {
            case DSJE_REPERROR:
                logger.error("An error occurred while trying to access the repository");
                break;
            case DSJE_NO_DATASTAGE:
                logger.error("An error occurred while trying to access the repository");
                break;
        /*
         * case DSJE_UNKNOWN_JOBNAME: logger.error(
		 * "When the RelationshipType is DSS_JOB_USES_JOB or DSS_JOB_USEDBY_JOB the supplied job name cannot be found in the project."
		 * ); break;
		 */
            default:
                break;
        }
        if (returnum == 0) {
            return null;
        }
        return prop.GetPointer(
                ((libvmdsapi.DSREPOSUSAGEJOB) ReturnInfo.info.readField("jobs")).jobname)
                .replaceAll("  +", "");
    }

    /**
     *
     * */
    public int DSGetStageInfo(libvmdsapi.DSJOB JobHandle,
                              String StageName, int InfoType, libvmdsapi.DSSTAGEINFO ReturnInfo) {
        if (JobHandle == null) {
            logger.error("Job Handle {}is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        //libvmdsapi.DSJOB JobHandle = this.DSOpenJob(JobName);
        int returnnum = this.getSession().DSGetStageInfo(JobHandle, StageName, InfoType,
                ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                break;
            case DSJE_NOT_AVAILABLE:
                logger.error("There are no instances of the requested information in the stage.");
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.s");
                break;
            case DSJE_BADSTAGE:
                logger.error("StageName does not refer to a known stage in the job.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid InfoType.");
                break;
            default:
                break;
        }
        return 0;
    }

    /**
     *
     * */
    public int DSGetVarInfo(libvmdsapi.DSJOB JobHandle, String StageName,
                            String VarName, int InfoType, libvmdsapi.DSVARINFO ReturnInfo) {
        if (JobHandle == null) {
            logger.error("Job Handle {}is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSGetVarInfo(JobHandle, StageName, VarName,
                InfoType, ReturnInfo);
        switch (returnnum) {
            case DSJE_NOERROR:
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_BADSTAGE:
                logger.error("StageName does not refer to a known stage in the job.");
                break;
            case DSJE_BADVAR:
                logger.error("VarName does not refer to a known variable in the job.");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid InfoType.");
                break;
            default:
                break;
        }
        return 0;
    }

    // FrangSang 2014-05-14

    /**
     *
     * */
    public String DSListEnvVars() {
        Pointer p = this.getSession().DSListEnvVars(ProjectHandle);
        String EnvVars = prop.GetPointer(p).replaceAll("  +", "");
        return EnvVars;
    }

    // FrangSang 2014-05-14

    /**
     *
     * */
    public String DSListProjectProperties() {
        String ProjectProperties = prop.GetPointer(
                this.getSession().DSListProjectProperties(ProjectHandle)).replaceAll("  +", "");
        return ProjectProperties;
    }

    // FrangSang 2014-05-12

    /**
     *
     * */
    public void DSLockJob(libvmdsapi.DSJOB JobHandle) {
        if (JobHandle == null) {
            logger.error("Job Handle {}is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSLockJob(JobHandle);
        if (returnnum == 0) {
            // System.out.println("Locked Job Sucessfully");
        } else {
            logger.error("DSLockJob:DSJE_BADHANDLE");
        }
    }

    // Adds a new entry to a job log file.

    /**
     *
     * */
    public void DSLogEvent(libvmdsapi.DSJOB JobHandle, int EventType,
                           String Message) {
        if (JobHandle == null) {
            logger.error("Job Handle {}is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSLogEvent(JobHandle, EventType, null, Message);
        /**
         * DSJ_LOGINFO
         * DSJ_LOGWARNING
         * */
        //System.out.println();
        switch (returnnum) {
            case DSJE_NOERROR:
                logger.info("DSLogEvent for job {} sucessfully ",JobHandle);
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_SERVER_ERROR:
                logger.error("Internal error. Engine returned invalid data");
                break;
            case DSJE_BADTYPE:
                logger.error("Invalid EventType value");
                break;
            default:
                break;
        }

    }

    //

    /**
     * 获取 JOB Report
     * <p/>
     * *************************************************
     * STATUS REPORT FOR JOB: test_gcc
     * Generated: 2015-10-12 00:42:36
     * Job start time=2015-10-11 15:40:36
     * Job end time=2015-10-11 15:40:36
     * Job elapsed time=00:00:00
     * Job status=1 (Finished OK)
     */
    public String DSMakeJobReport(libvmdsapi.DSJOB JobHandle) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
/*        public void DSMakeJobReport(libvmdsapi.DSJOB JobHandle, int ReportType,
                                String LineSeparator, libvmdsapi.DSREPORTINFO ReturnInfo) {*/
        libvmdsapi.DSREPORTINFO ReturnInfo = new libvmdsapi.DSREPORTINFO();
        this.getSession().DSMakeJobReport(JobHandle, 0, "LF", ReturnInfo);
        //System.out.println(prop.GetPointer(ReturnInfo.info.reportText).replaceAll(" +"," "));
        return prop.GetPointer(ReturnInfo.info.reportText).replaceAll(" +", " ");

    }

    /**
     *
     * */
    public libvmdsapi.DSJOB DSOpenJob(String JobName) /*throws JobOpenException*/ {
        if (!(this.DSGetJoblist().contains(JobName.split("\\.")[0]))) {
            logger.error(" The Job  {} not exists in the project \" {} \"",JobName,this.ProjectName);
            throw new JobNotFoundException(" The Job " + JobName + " not exists in the project \"" + this.ProjectName + "\"");
        }

        libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        if (job == null) {
            int returnnum = this.getSession().DSGetLastError();
            //System.out.println(returnnum);
            switch (returnnum) {
                case DSJE_OPENFAIL:
                    logger.error("Attempt to open job failed ");
                    throw new JobOpenException("Attempt to open job " + JobName + " failed,pls have a check !!!");
                    //   throw new JobOpenException("The Job "+JobName + " is not exists Or not be Compiled");
                    //break;
                case DSJE_NO_MEMORY:
                    logger.error("Malloc failure");
                    throw new OutofMemoryException("Malloc failure When Open Job " + JobName);
                    //break;
                default:
                    logger.error("There is a Unkonw Error {} When Open The job {} pls check !!!",returnnum,JobName);
                    throw new UnknowException("There is a Unkonw Error " + returnnum + " When Open The job" + JobName + " pls check !!!");
                    //break;
            }
        }
        return job;
    }

    // DSOpenProject
    // FrangSang 2014-05-11

    /**
     *
     * */
    public libvmdsapi.DSPROJECT DSOpenProject(String ProjectName) throws ProjectNoFoundException {

        libvmdsapi.DSPROJECT project = this.getSession().DSOpenProjectEx(DSAPI_VERSION, ProjectName);
        if (project == null) {
            int errornum = this.getSession().DSGetLastError();
            switch (errornum) {
                case DSJE_BADPROJECT:
                    logger.error("Unknown project name.");
                    throw new ProjectNoFoundException("Unknown project name. " + ProjectName);
                    //break;
                case DSJE_NO_DATASTAGE:
                    logger.error("DataStage not installed on server.");
                    break;
                case DSJE_SERVER_ERROR:
                    logger.error("Server generated error - error msg text desribes it");
                    break;
                case DSJE_BAD_VERSION:
                    logger.error("Version is DSOpenProjectEx is invalid.");
                    break;
                case DSJE_INCOMPATIBLE_SERVER:
                    logger.error("Server version incompatible with this version of the DSAPI.");
                    break;
                default:
                    logger.error("Unknown ErrorNum.");
                    break;
            }
        }
        return project;
    }

    /**
     *
     * */
    public void DSRunJob(libvmdsapi.DSJOB JobHandle) {
        if (JobHandle == null) {
            logger.error("Job Handle is null");
            throw new JobHandleException("Job Handle is null");
        }
        //libvmdsapi.DSJOB Jobhandle = this.DSOpenJob(JobName);
/*        this.getSession().DSLockJob(Jobhandle);
        this.getSession().DSRunJob(Jobhandle, DSJ_RUNRESET);
        this.getSession().DSWaitForJob(Jobhandle);
        this.getSession().DSUnlockJob(Jobhandle);*/

        if (!this.DSChkJobRestartable(JobHandle)) {
            logger.error("Job can not restartable ,pls have a chk");
            throw new JobStatusException("Job can not restartable ,pls have a chk");
        }

        this.getSession().DSLockJob(JobHandle);
        int returnnum = this.getSession().DSRunJob(JobHandle, DSJ_RUNNORMAL);
        this.getSession().DSWaitForJob(JobHandle);
        this.getSession().DSUnlockJob(JobHandle);
        //this.getSession().DSCloseJob(JobHandle);

        if (returnnum != 0) {
            switch (returnnum) {
                case DSJE_BADHANDLE:
                    logger.error("DSRunJob:Invalid JobHandle.");
                    break;
                case DSJE_BADSTATE:
                    logger.error("DSRunJob:Job is not in the right state (must be compiled & not running). ");
                    break;
                case DSJE_BADTYPE:
                    logger.error("DSRunJob:Invalid EventType value");
                    break;
                case -1006:
                    logger.error("DSRunJob:Server generated error - error msg text desribes it .");
                    break;
                default:
                    break;
            }
        }

    }

    /**
     *
     * */
    public void DSSetEnvVar(String EnvVarName, String Value) {
        int returnnum = this.getSession().DSSetEnvVar(ProjectHandle, EnvVarName, Value);
        switch (returnnum) {
            case DSJE_NOERROR:
                break;
            case DSJE_READENVVARDEFNS:
                logger.error("failed to read environment variable definitions");
                break;
            case DSJE_READENVVARVALUES:
                logger.error("failed to read environment variable values");
                break;
            case DSJE_BADENVVAR:
                logger.error("failed to read environment variable definitions");
                break;
            case DSJE_WRITEENVVARVALUES:
                logger.error("failed to write environment variable values");
                break;
            case DSJE_ENCODEFAILED:
                logger.error("failed to encode an encrypted value");
                break;
            case DSJE_BADBOOLEANVALUE:
                logger.error("invalid value given for a boolean environment variable");
                break;
            case DSJE_BADNUMERICVALUE:
                logger.error("invalid value given for an integer environment variable");
                break;
            case DSJE_BADLISTVALUE:
                logger.error("invalid value given for an environment variable with a fixed list of values");
                break;
            case DSJE_PXNOTINSTALLED:
                logger.error("environment variable is specific to parallel jobs which	are not available");
                break;
            case DSJE_ISPARALLELLICENCED:
                logger.error("failed to determine if parallel jobs are available");
                break;
            default:
                break;
        }
    }

    //
    public void DSSetGenerateOpMetaData(libvmdsapi.DSJOB JobHandle, boolean value) {

    }

    /**
     *
     * */
    public void DSSetJobLimit(libvmdsapi.DSJOB JobHandle, int LimitType,
                              int LimitValue) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSSetJobLimit(JobHandle, LimitType, LimitValue);
        switch (returnnum) {
            case DSJE_NOERROR:
                break;
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle.");
                break;
            case DSJE_BADSTATE:
                logger.error("Job is not in the right state (compiled, not running).");
                break;
            case DSJE_BADTYPE:
                logger.error("LimitType is not the name of a known limiting condition.");
                break;
            case DSJE_BADVALUE:
                logger.error("LimitValue is not appropriate for the limiting condition type.");
                break;
            case DSJE_SERVER_ERROR:
                logger.error("Internal error. Engine returned invalid data.");
                break;
            default:
                break;
        }
    }

    /*
     * public void DSSetParam(DSJOB JobHandle, String ParamName, DSPARAM Param)
     * {
     *
     * }
     */
    //
    public void DSSetProjectProperty(libvmdsapi.DSPROJECT hProject, String Property,
                                     String Value) {

    }

    //
/*    public void DSSetServerParams(String DomainName, String UserName,
                                  String Password, String ServerName) {

    }*/

    /**
     *
     * */
    public void DSStopJob(libvmdsapi.DSJOB JobHandle) {
        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSStopJob(JobHandle);
        switch (returnnum) {
           /* case DSJE_NOERROR:
                break;*/
            case DSJE_BADHANDLE:
                logger.error("Invalid JobHandle");
                break;
            default:
                break;
        }
    }

    // FrangSang 2014-05-12

    /**
     *
     * */
    public void DSUlockJob(libvmdsapi.DSJOB JobHandle) {


        if (JobHandle == null) {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSUnlockJob(JobHandle);
        if (returnnum != 0) {
            logger.error("DSUlockJob:DSJE_BADHANDLE Invalid JobHandle.");
        }
    }

    /**
     *
     * */
    public void DSWaitForJob(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        int returnnum = this.getSession().DSWaitForJob(JobHandle);
        if (returnnum != 0) {
            switch (returnnum) {
                case DSJE_BADHANDLE:
                    logger.error("DSWaitForJob:Invalid JobHandle");
                    break;
                case DSJE_WRONGJOB:
                    logger.error("DSWaitForJob:Job for this JobHandle was not started from a call to DSRunJob by the current process.");
                    break;
                case DSJE_TIMEOUT:
                    logger.error("DSWaitForJob:Job appears not to have started after waiting for a reasonable length of. time. (About 30 minutes.)");
                    break;
                default:
                    break;
            }
        }
    }

    // eventtype enevt
    // FrangSang 2014-05-11

    /**
     *
     * */
    public String DSGetEventValue(int EventType) {
        String EventValue;
        switch (EventType) {
            case DSJ_LOGINFO:
                EventValue = "Info";
                break;
            case DSJ_LOGWARNING:
                EventValue = "Warning";
                break;
            case DSJ_LOGFATAL:
                EventValue = "Fatal";
                break;
            case DSJ_LOGREJECT:
                EventValue = "Reject";
                break;
            case DSJ_LOGSTARTED:
                EventValue = "Control";
                break;
            case DSJ_LOGRESET:
                EventValue = "Reset";
                break;
            case DSJ_LOGBATCH:
                EventValue = "Batch";
                break;
            case DSJ_LOGOTHER:
                EventValue = "Other";
                break;
            case DSJ_LOGANY:
                EventValue = "Any";
                break;
            default:
                EventValue = "Unknow";
                break;
        }
        return EventValue;
    }

    // FrangSang 2014-05-11
    /**
     *
     * */

  /*   public String DSGetJobLog(String projectname, String Jobname) {
        libvmdsapi.DSPROJECT project = this.getSession().DSOpenProjectEx(DSAPI_VERSION, projectname);
        libvmdsapi.DSJOB job = this.getSession().DSOpenJob(project, Jobname);
        int[] logeventids = this.DSGetLogEventIds(job, 0, "");
        libvmdsapi.DSLOGDETAIL Event = new libvmdsapi.DSLOGDETAIL();
        String JobLog = "             Inforsphere datastage log "
                + prop.line_separate
                + "===================================================================="
                + prop.line_separate;
        for (int i = 0; i < logeventids.length; i++) {
            // System.out.println(logeventids[i]);
            this.DSGetLogEntry(job, logeventids[i], Event);
            *//*
             * System.out.println(prop.getTimeStamp(Event.timestamp.value) +
			 * " " + this.DSGetEventValue(Event.type) + " " +
			 * prop.GetPointer(Event.fullMessage));
			 *//*
            JobLog = JobLog + prop.getTimeStamp(Event.timestamp.value) + " "
                    + this.DSGetEventValue(Event.type) + " "
                    + prop.GetPointer(Event.fullMessage)
                    + prop.line_separate;
        }
        return JobLog;
    }
*/

    /**
     * DSGetJobLog(String Jobname) ֱ
     *
     * */
/*
    public String DSGetJobLog(String Jobname) {
        //libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, Jobname);
        libvmdsapi.DSJOB job = this.DSOpenJob(Jobname);
        int[] logeventids = this.DSGetLogEventIds(job, 0, "");
        libvmdsapi.DSLOGDETAIL Event = new libvmdsapi.DSLOGDETAIL();
        String JobLog = "             InforSphere DataStage log "
                + prop.line_separate
                + "===================================================================="
                + prop.line_separate;
        for (int i = 0; i < logeventids.length; i++) {
            this.DSGetLogEntry(job, logeventids[i], Event);
            JobLog = JobLog + prop.getTimeStamp(Event.timestamp.value) + " "
                    + this.DSGetEventValue(Event.type) + " "
                    + prop.GetPointer(Event.fullMessage)
                    + prop.line_separate;
        }
        return JobLog;
    }
*/

    /**
     *
     * */
    public String DSGetJobLog(/*DSPROJECT projectname,*/
                              libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        //System.out.println(Jobname);
        int[] logeventids = this.DSGetLogEventIds(JobHandle, 0, "");
        libvmdsapi.DSLOGDETAIL Event = new libvmdsapi.DSLOGDETAIL();
        String JobLog = "             Inforsphere DataStage log "
                + prop.line_separate
                + "===================================================================="
                + prop.line_separate;
        for (int i = 0; i < logeventids.length; i++) {
            this.DSGetLogEntry(JobHandle, logeventids[i], Event);
            JobLog = JobLog + prop.getTimeStamp(Event.timestamp.value) + " "
                    + this.DSGetEventValue(Event.type) + " "
                    + prop.GetPointer(Event.fullMessage)
                    + prop.line_separate;
        }
        return JobLog;
    }

    // FrangSang 2014-05-11

    /**
     *
     * */
    public int DSGetJobPid(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        //libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        this.DSGetJobInfo(JobHandle, DSJ_JOBPID, jobinfo);
        int jobPid = jobinfo.info.jobPid;
        //System.out.println(JOBSTATUS);
        return jobPid;
    }

    // FrangSang 2014-05-11

    /**
     *
     * */
    public String DSGetJobStatus(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        //libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        this.DSGetJobInfo(JobHandle, DSJ_JOBSTATUS, jobinfo);
        int JOBSTATUS = jobinfo.info.jobStatus;
        //System.out.println(JOBSTATUS);
        return DSJS.get(JOBSTATUS);
    }

    // FrangSang 2014-05-11

    /**
     *
     * */
    public String DSGetJobParamList(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        this.DSGetJobInfo(JobHandle, DSJ_PARAMLIST, jobinfo);
        return prop.GetPointer(jobinfo.info.paramList).replaceAll(" +", "");
    }

    // FrangSang 2014-05-11

    /**
     *
     * */
    public String DSGetJobInvocations(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        //libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        this.DSGetJobInfo(JobHandle, DSJ_JOBINVOCATIONS, jobinfo);
        //this.getSession().DSCloseJob(JobHandle);
        return prop.GetPointer(jobinfo.info.jobInvocations).replaceAll(" +",
                "");
    }

	/*public String DSGetJob(DSJOB job) {
        DSJOBINFO jobinfo = new DSJOBINFO();
		this.DSGetJobInfo(lib, job, DSJ_JOBINVOCATIONS, jobinfo);
		return prop.GetPointer(jobinfo.info.jobInvocations).replaceAll(" +",
				"");
	}*/

/*
    // FrangSang 2014-05-11���Գɹ�
    //FrankSang 2015-10-11 ע�͵������е�job���ַ��ʽ����

    public String DSGetJobElapsed(DSJOB job) {
        DSJOBINFO jobinfo = new DSJOBINFO();
        this.DSGetJobInfo(job, DSJ_JOBELAPSED, jobinfo);
        return prop.getJobElapsed(jobinfo.info.jobElapsed);
    }
*/

    // FrangSang 2014-05-11���Գɹ�

    /**
     *
     * */
    public String DSGetJobElapsed(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
/*
        libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
*/
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        this.DSGetJobInfo(JobHandle, DSJ_JOBELAPSED, jobinfo);
        //this.getSession().DSCloseJob(job);
        return prop.getJobElapsed(jobinfo.info.jobElapsed);
    }

/*	public String DSGetJobElapsed1(DSPROJECT hproject,
            String JobName) {
		DSJOBINFO1 jobinfo = new DSJOBINFO1();
		DSJOB job = lib.DSOpenJob(hproject, JobName);
		this.DSGetJobInfo1(lib, job, DSJ_JOBELAPSED, jobinfo);
		lib.DSCloseJob(job);
		return prop.getJobElapsed();
	}*/

    //

    /**
     *
     * */
    public String DSGetJobStartTime(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        //libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        this.DSGetJobInfo(JobHandle, DSJ_JOBSTARTTIMESTAMP, jobinfo);
        //System.out.println(jobinfo.info.readField("jobLastTime"));
        //System.out.println(jobinfo);
        //this.getSession().DSCloseJob(JobHandle);
        //System.out.println(Long.toString(jobinfo.info.jobLastTime.value));
        //System.out.println(String.valueOf(jobinfo.info.jobLastTime.value));
        return prop.getTimeStamp(jobinfo.info.jobStartTime);

    }

    /**
     *
     * */
    public String DSGetJobLastTime(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        // libvmdsapi.DSJOB job = this.getSession().DSOpenJob(ProjectHandle, JobName);
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        this.DSGetJobInfo(JobHandle, DSJ_JOBLASTTIMESTAMP, jobinfo);
        //System.out.println(jobinfo.info.readField("jobLastTime"));
        //System.out.println(jobinfo);
        //this.getSession().DSCloseJob(job);
        //System.out.println(Long.toString(jobinfo.info.jobLastTime.value));
        //System.out.println(String.valueOf(jobinfo.info.jobLastTime.value));
        return prop.getTimeStamp(jobinfo.info.jobLastTime);

    }

    // DSJ_JOBSTARTTIMESTAMP

    // FrangSang 2014-05-11
    /**
     *
     * */
    public boolean DSChkJobMultiInvokable(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
/*
        libvmdsapi.DSJOB job = this.session.DSOpenJob(ProjectHandle, JobName);
*/
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        boolean flag = false;
        this.getSession().DSGetJobInfo(JobHandle, DSJ_JOBMULTIINVOKABLE, jobinfo);
        // this.getSession().DSCloseJob(JobHandle);
        // System.out.println(jobinfo.info.jobMultiInvokable);
        if (jobinfo.info.jobMultiInvokable == 1)
            flag = true;
        return flag;
    }

    // FrangSang 2014-05-11

    /**
     *
     * */
    public boolean DSChkJobRestartable(libvmdsapi.DSJOB JobHandle) {
        if(JobHandle==null)
        {
            logger.error("Job Handle {} is null",JobHandle);
            throw new JobHandleException("Job Handle is null");
        }
        libvmdsapi.DSJOBINFO jobinfo = new libvmdsapi.DSJOBINFO();
        //libvmdsapi.DSJOB job = this.DSOpenJob(JobName);
        boolean flag = false;
        this.DSGetJobInfo(JobHandle, DSJ_JOBRESTARTABLE, jobinfo);
        //System.out.println(jobinfo.info.jobRestartable);
        //this.getSession().DSCloseJob(job);
        //System.out.println(jobinfo.info.jobRestartable);
        if (jobinfo.info.jobRestartable == 0)
            flag = true;
        return flag;
    }
}

