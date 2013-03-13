package org.erlide.runtime.rpc;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.google.common.util.concurrent.CheckedFuture;

public interface IRpcFuture extends
        CheckedFuture<OtpErlangObject, RpcException> {

    OtpErlangObject get(long timeout);

    OtpErlangObject checkedGet(long timeout) throws RpcException;

}
