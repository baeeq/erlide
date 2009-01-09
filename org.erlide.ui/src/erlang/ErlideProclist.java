package erlang;

import org.erlide.runtime.ErlLogger;
import org.erlide.runtime.backend.Backend;
import org.erlide.runtime.backend.exceptions.NoBackendException;

import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangPid;

public class ErlideProclist {
	public static final String MODULE_NAME = "erlide_proclist";

	public static void processListInit(Backend b) {
		if (b == null) {
			return;
		}
		try {
			b.rpc(MODULE_NAME, "process_list_init", "");
		} catch (final Exception e) {
			ErlLogger.debug(e);
		}
	}

	public static OtpErlangList getProcessList(Backend b) {
		if (b == null) {
			return new OtpErlangList();
		}
		try {
			return (OtpErlangList) b.rpcx(MODULE_NAME, "process_list", "");
		} catch (final NoBackendException e) {
			ErlLogger.debug(e);
		} catch (final Exception e) {
			ErlLogger.warn(e);
		}
		return new OtpErlangList();
	}

	public static OtpErlangObject getProcessInfo(Backend b,
			OtpErlangPid pid) {
		if (b == null) {
			return new OtpErlangAtom("error");
		}
		try {
			return b.rpcx(MODULE_NAME, "get_process_info", "p", pid);
		} catch (final NoBackendException e) {
			ErlLogger.debug(e);
		} catch (final Exception e) {
			ErlLogger.warn(e);
		}
		return new OtpErlangAtom("error");
	}

}
