package com.ava.dynamic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ava.dynamic.model.widget.StatBoxData;

@Service
public class StatBoxService {
	public static List<StatBoxData> getStatBoxDataPrototype(int size, double maxVal, String[] desc) {
		if (size < 1) {
			size = 1;
		}
		String[] defaultDesc = { "Capacity", "Today forecase app in", "Incoming app (Urgen/All)", "Input", "Station on hand(Urgent/WIP)", "Output" };
		if(desc == null){
			desc = defaultDesc;
		}
		int maxDescIndex = desc.length-1;
		List<StatBoxData> data = new ArrayList<StatBoxData>();
		for (int i = 0; i < size; i++) {
			StatBoxData stat = new StatBoxData();
			stat.setValue1(Math.random() * maxVal);
			stat.setValue2(Math.random() * maxVal);
			stat.setValue3(Math.random() * maxVal);
			stat.setValue4(Math.random() * maxVal);
			stat.setFirstBoxUnit(desc[Math.min(i, maxDescIndex)]);
			stat.setSecondBoxUnit(desc[Math.min(i, maxDescIndex)]);
			
			//Default for Double Stat Box
			stat.setFirstBoxUnitDesc("Actual/Target");
			stat.setSecondBoxUnitDesc("Actual/Target");
			data.add(stat);
		}
		return data;
	}
}
