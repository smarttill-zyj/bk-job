/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.tencent.bk.job.execute.api.web.impl;

import com.tencent.bk.job.common.constant.ErrorCode;
import com.tencent.bk.job.common.exception.InvalidParamException;
import com.tencent.bk.job.execute.model.web.vo.ExecuteFileSourceInfoVO;
import com.tencent.bk.job.manage.common.consts.globalsetting.RestrictModeEnum;
import com.tencent.bk.job.manage.model.web.vo.globalsetting.FileUploadSettingVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


public class Test {
        public static void main(String[] args) {
            test();
        }
      static void  test(){
            FileUploadSettingVO fileUploadSettingVO = new FileUploadSettingVO();
            fileUploadSettingVO.setRestrictMode(0);
            List<String> suffixListd = new ArrayList<>();
            suffixListd.add(".txtd");
            fileUploadSettingVO.setSuffixList(suffixListd);
            Integer restrictMode = fileUploadSettingVO.getRestrictMode();
            List<String> suffixList = fileUploadSettingVO.getSuffixList();
            Boolean validateSuffix = true;
            //初始状态
            if (CollectionUtils.isNotEmpty(suffixList)) {
                if (restrictMode == RestrictModeEnum.ALLOW.getType()) {
                    validateSuffix = false;
                } else {
                    validateSuffix = true;
                }
            }
            List<ExecuteFileSourceInfoVO> fileSourceList = new ArrayList<>();
            ExecuteFileSourceInfoVO executeFileSourceInfoVO = new ExecuteFileSourceInfoVO();
            List<String> fileLocation = new ArrayList<>();
            fileLocation.add("8bf6b05f-a97e-425a-bf1b-0b1844a4a7ab/admin/新建文本文档.txt");
            executeFileSourceInfoVO.setFileLocation(fileLocation);
            fileSourceList.add(executeFileSourceInfoVO);
            for (ExecuteFileSourceInfoVO fileSource : fileSourceList) {
                if (CollectionUtils.isEmpty(fileSource.getFileLocation())) {
                    //log.warn("Fast send file ,files are empty");
                    //return false;
                }
                if (CollectionUtils.isNotEmpty(suffixList)) {
                    for (String fileSuffix : fileSource.getFileLocation()) {
                        for (String suffix : suffixList) {
                            if (fileSuffix.toLowerCase().endsWith(suffix.toLowerCase())) {
                                if (restrictMode == RestrictModeEnum.ALLOW.getType()) {
                                    validateSuffix = true;
                                } else {
                                    validateSuffix = false;
                                }
                            }
                        }
                    }
                }

            }
            if (!validateSuffix) {
                //log.warn("Fast send file, file suffix not allow");
                throw new InvalidParamException(ErrorCode.UPLOAD_FILE_SUFFIX_NOT_ALLOW);
            }

        }

}
