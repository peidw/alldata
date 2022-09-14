/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.manager.client;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.inlong.manager.client.api.ClientConfiguration;
import org.apache.inlong.manager.client.api.InlongClient;
import org.apache.inlong.manager.client.api.InlongGroup;
import org.apache.inlong.manager.client.api.InlongGroupContext;
import org.apache.inlong.manager.client.api.InlongStreamBuilder;
import org.apache.inlong.manager.common.enums.DataFormat;
import org.apache.inlong.manager.common.enums.FieldType;
import org.apache.inlong.manager.common.pojo.group.InlongGroupInfo;
import org.apache.inlong.manager.common.pojo.source.kafka.KafkaSource;
import org.apache.inlong.manager.common.pojo.stream.StreamField;
import org.apache.shiro.util.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test class for kafka to hive.
 */
@Slf4j
public class Kafka2HiveExample extends BaseExample {

    @Test
    public void testCreateGroupForHive() {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setWriteTimeout(10);
        configuration.setReadTimeout(10);
        configuration.setConnectTimeout(10);
        configuration.setTimeUnit(TimeUnit.SECONDS);
        configuration.setAuthentication(super.getInlongAuth());
        InlongClient inlongClient = InlongClient.create(super.getServiceUrl(), configuration);

        InlongGroupInfo groupInfo = super.createGroupInfo();
        try {
            InlongGroup group = inlongClient.forGroup(groupInfo);
            InlongStreamBuilder streamBuilder = group.createStream(createStreamInfo());
            streamBuilder.fields(createStreamFields());
            streamBuilder.source(createKafkaSource());
            streamBuilder.sink(createHiveSink());
            streamBuilder.initOrUpdate();
            // start group
            InlongGroupContext inlongGroupContext = group.init();
            Assert.notNull(inlongGroupContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStopGroup() {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setWriteTimeout(10);
        configuration.setReadTimeout(10);
        configuration.setConnectTimeout(10);
        configuration.setTimeUnit(TimeUnit.SECONDS);
        configuration.setAuthentication(super.getInlongAuth());
        InlongClient inlongClient = InlongClient.create(super.getServiceUrl(), configuration);
        InlongGroupInfo groupInfo = createGroupInfo();
        try {
            InlongGroup group = inlongClient.forGroup(groupInfo);
            InlongGroupContext groupContext = group.delete();
            Assert.notNull(groupContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private KafkaSource createKafkaSource() {
        KafkaSource kafkaSource = new KafkaSource();
        kafkaSource.setBootstrapServers("{kafka.bootstrap}");
        kafkaSource.setTopic("{kafka.topic}");
        kafkaSource.setSourceName("{kafka.source.name}");
        kafkaSource.setSerializationType(DataFormat.JSON.getName());
        return kafkaSource;
    }

    private List<StreamField> createStreamFields() {
        List<StreamField> streamFieldList = Lists.newArrayList();
        streamFieldList.add(new StreamField(0, FieldType.STRING.toString(), "name", null, null));
        streamFieldList.add(new StreamField(1, FieldType.INT.toString(), "age", null, null));
        return streamFieldList;
    }
}