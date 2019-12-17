// IIPCService.aidl
package com.zthx.ipc;
import com.zthx.ipc.model.Request;
import com.zthx.ipc.model.Response;

interface IIPCService {

    Response send(in Request request);
}
