package com.dingco.pedal.validation;

import com.dingco.pedal.validation.group.NotBlankGroup;
import com.dingco.pedal.validation.group.PatternCheckGroup;

import javax.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {

}
