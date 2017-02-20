package org.flowable.editor.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.junit.Test;

public class CatchEventConverterTest extends AbstractConverterTest {

    @Test
    public void connvertJsonToModel() throws Exception {
        BpmnModel bpmnModel = readJsonFile();
        validateModel(bpmnModel);
    }

    @Test
    public void doubleConversionValidation() throws Exception {
        BpmnModel bpmnModel = readJsonFile();
        bpmnModel = convertToJsonAndBack(bpmnModel);
        validateModel(bpmnModel);
    }

    protected String getResource() {
        return "test.catcheventmodel.json";
    }

    private void validateModel(BpmnModel model) {

        FlowElement timerElement = model.getMainProcess().getFlowElement("timer_evt", true);
        EventDefinition timerEvent = extractEventDefinition(timerElement);
        assertTrue(timerEvent instanceof TimerEventDefinition);
        TimerEventDefinition ted = (TimerEventDefinition) timerEvent;
        assertEquals("PT5M", ted.getTimeDuration());

        FlowElement signalElement = model.getMainProcess().getFlowElement("signal_evt", true);
        EventDefinition signalEvent = extractEventDefinition(signalElement);
        assertTrue(signalEvent instanceof SignalEventDefinition);
        SignalEventDefinition sed = (SignalEventDefinition) signalEvent;
        assertEquals("signal_ref", sed.getSignalRef());

        FlowElement messageElement = model.getMainProcess().getFlowElement("message_evt", true);
        EventDefinition messageEvent = extractEventDefinition(messageElement);
        assertTrue(messageEvent instanceof MessageEventDefinition);
        MessageEventDefinition med = (MessageEventDefinition) messageEvent;
        assertEquals("message_ref", med.getMessageRef());

    }

}