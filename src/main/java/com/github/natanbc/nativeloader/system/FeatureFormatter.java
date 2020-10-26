package com.github.natanbc.nativeloader.system;

import java.util.Map;
import java.util.stream.Collectors;

class FeatureFormatter {
    static String formatFeatures(CPUInfo info) {
        return info.features().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
