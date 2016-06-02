/**
 * Copyright 2016 Landoop.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.landoop.avrogenerator;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaTools {

  private static final Logger log = LoggerFactory.getLogger(KafkaTools.class);

  public static void createTopic(String zookeepers, String topicName, int noOfPartitions, int noOfReplication) {
    ZkClient zkClient = null;
    int sessionTimeOutInMs = 3 * 1000;
    int connectionTimeOutInMs = 3 * 1000;
    try {
      log.info("Creating topic [" + topicName + "] with " + noOfPartitions + " partitions and " + noOfReplication);
      zkClient = new ZkClient(zookeepers, sessionTimeOutInMs, connectionTimeOutInMs, ZKStringSerializer$.MODULE$);
      ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeepers), false);
      Properties topicConfiguration = new Properties();
      AdminUtils.createTopic(zkUtils, topicName, noOfPartitions, noOfReplication, topicConfiguration);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (zkClient != null) {
        zkClient.close();
      }
    }
  }
}