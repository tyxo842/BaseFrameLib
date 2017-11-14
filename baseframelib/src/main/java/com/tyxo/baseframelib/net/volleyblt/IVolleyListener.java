package com.tyxo.baseframelib.net.volleyblt;

import com.android.volley.VolleyError;

public interface IVolleyListener<T> {

	void onResponse(T response);
	void onErrorResponse(VolleyError error);
//	void onCookieTimeOut();
}
