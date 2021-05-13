package com.muc;

import java.io.IOException;

public interface MessageListener {
    public void onMessage(String fromLogin, String msgBody) throws IOException;
}
