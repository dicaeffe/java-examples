/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.demo.exploration.java;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
public class ScheduledTasks2 {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks2.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	/* The @Scheduled can be configured with many options. Some examples:
	 * - 'fixedRate' specifies the interval between method invocations, measured from the start time of each invocation.
	 * - 'fixedDelay' specifies the interval between invocations measured from the completion of the task.
	 * - 'cron' can be used for sophisticated task scheduling. */
	@Scheduled(fixedRate = 1000)
	public void reportCurrentTimeRate() {
		print(EType.RATE);
	}
	
	@Scheduled(fixedDelay = 1000)
	public void reportCurrentTimeDelay() {
		print(EType.DELAY);
	}
	
	
	private void print(EType type) {
		try {
			java.lang.Thread.sleep(300);
			log.info("{} {} The time is now {}", type.id, type.div, DATE_FORMAT.format(new Date()));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@AllArgsConstructor
	private enum EType {
		RATE	("[R]", "---"),
		DELAY	("[D]", "+++"),
		;
		private String id;
		private String div;
	}
}