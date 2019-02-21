package com.zkkc.track.moudle.pic.callback;

import java.io.File;
import java.util.List;

/**
 * Created by ShiJunRan on 2019/2/19
 */
public interface IPicResult {
    void Succeed(List<File> files);

    void Failure(String eStr);

}
