package com.ynov.apprecipe.interfaces;

import java.util.ArrayList;

// Une arraylist peut contenir tout type en mettant T je leve le type

public interface IAsyncTaskCallback<T> {
    void onPreExecute();
    void onErrorOccured(Exception ex);
    void onPostExecute(ArrayList<T> objects);
}
