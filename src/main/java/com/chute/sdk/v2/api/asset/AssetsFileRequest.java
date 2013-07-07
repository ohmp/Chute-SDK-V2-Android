package com.chute.sdk.v2.api.asset;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import android.content.Context;
import android.util.Log;

import com.chute.sdk.v2.api.parsers.ListResponseParser;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.chute.sdk.v2.utils.RestConstants;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.client.BaseRestClient.RequestMethod;
import com.dg.libs.rest.requests.FileBodyHttpRequestImpl;

public class AssetsFileRequest extends FileBodyHttpRequestImpl<ListResponseModel<AssetModel>> {

	private String filePath;
	private AlbumModel album;

	public AssetsFileRequest(Context context, AlbumModel album, String filePath,
			HttpCallback<ListResponseModel<AssetModel>> callback) {
		super(context, RequestMethod.POST, new ListResponseParser<AssetModel>(AssetModel.class), callback);
		this.filePath = filePath;
		this.album = album;
		if (album == null) {
			throw new NullPointerException("Album cannot be null");
		}
	}
	
	@Override
	public HttpEntity getEntity() {
		MultipartEntity multipartEntity = new MultipartEntity();
		Log.d("debug", "file path = " + fileToSend().getPath());
		multipartEntity.addPart("filedata", new FileBody(fileToSend()));
		return multipartEntity;
	}
	
	@Override
	protected void doAfterRunRequestInBackgroundThread() {
		super.doAfterRunRequestInBackgroundThread();
		Log.d("debug", "entity body = " + getEntity().getContentType().toString());
	}
	
	

	@Override
	public File fileToSend() {
		File file = new File(filePath);
		return file;
	}

	@Override
	protected String getUrl() {
		return String.format(RestConstants.URL_UPLOAD_ONE_STEP, album.getId());
	}

}