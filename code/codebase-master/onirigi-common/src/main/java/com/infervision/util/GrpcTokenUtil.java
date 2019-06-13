package com.infervision.tools.common.util;

import io.grpc.Metadata;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.MetadataUtils;

/**
 * @author: fruiqi
 * @date: 19-5-15 上午10:24
 * @version:1.0 携带 token grpc
 **/
public class GrpcTokenUtil {

	/**
	 * 自定义 token内容
	 *
	 * @param stub
	 * @return: T
	 * @author: fruiqi
	 * @date: 19-5-15 上午11:02
	 */
	public static <T extends AbstractStub<T>> T blockingStubToken(T stub, String token) {
		Metadata auth = TokenExtractor.createTokenMetadata(token);
		return MetadataUtils.attachHeaders(stub, auth);
	}

}
