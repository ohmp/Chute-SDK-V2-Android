// Copyright (c) 2011, Chute Corporation. All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without modification,
//  are permitted provided that the following conditions are met:
//
//     * Redistributions of source code must retain the above copyright notice, this
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
//
package com.chute.sdk.v2.api.album;

import android.text.TextUtils;
import com.araneaapps.android.libs.logger.ALog;
import com.chute.sdk.v2.api.base.BaseStringBodyHttpRequest;
import com.chute.sdk.v2.api.parsers.ResponseParser;
import com.chute.sdk.v2.model.AlbumModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.chute.sdk.v2.utils.RestConstants;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.client.BaseRestClient.RequestMethod;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumsUpdateRequest extends
    BaseStringBodyHttpRequest<ResponseModel<AlbumModel>> {

  public static final String TAG = AlbumsUpdateRequest.class.getSimpleName();
  private AlbumModel album;
  private AssetModel asset;

  public AlbumsUpdateRequest(AlbumModel album, AssetModel asset,
      HttpCallback<ResponseModel<AlbumModel>> callback) {
    super(RequestMethod.PUT, new ResponseParser<AlbumModel>(
        AlbumModel.class), callback);
    if (TextUtils.isEmpty(album.getId())) {
      throw new IllegalArgumentException(
          "Need to provide album ID for editing");
    }
    this.album = album;
  }

  @Override
  public String bodyContents() {
    String bodyContents = null;
    if (asset != null) {
      JSONObject json = new JSONObject();
      try {
        json.put("name", album.getName());
        json.put("cover_asset_id", asset.getId());
        bodyContents = json.toString();
      } catch (JSONException e) {
        ALog.e("JSONException: " + e.getMessage());
      }
    } else {
      bodyContents = this.album.serializeAlbum();
    }
    return bodyContents;
  }

  @Override
  protected String getUrl() {
    return String.format(RestConstants.URL_ALBUMS_UPDATE, album.getId());
  }

}
