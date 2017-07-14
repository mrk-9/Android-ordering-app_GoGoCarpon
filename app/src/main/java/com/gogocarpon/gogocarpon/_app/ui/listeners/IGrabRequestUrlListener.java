package com.gogocarpon.gogocarpon._app.ui.listeners;

public interface IGrabRequestUrlListener<T, P> {
	public void onAsyncTaskBegin();
	public void onAsyncTaskDoBackground(String... url);
	public void onAsyncTaskComplete(T contentData, P messageID);
	public void onAsyncTaskError(T contentData, P messageID);
}