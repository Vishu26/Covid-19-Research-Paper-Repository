package com.example.covid_19;

import java.util.ArrayList;
import java.util.List;

public interface AysncResponse {
    void processFinish(List<Task> taskList);
}
