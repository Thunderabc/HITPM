
package hitpm_v2.ICES_beans_bpmn.converter;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import hitpm_v2.ICES_beans_bpmn.converter.util.BpmnXMLUtil;
import hitpm_v2.ICES_beans_bpmn.model.Association;
import hitpm_v2.ICES_beans_bpmn.model.AssociationDirection;
import hitpm_v2.ICES_beans_bpmn.model.BaseElement;
import hitpm_v2.ICES_beans_bpmn.model.BpmnModel;

import org.apache.commons.lang3.StringUtils;

public class AssociationXMLConverter extends BaseBpmnXMLConverter {

  public Class<? extends BaseElement> getBpmnElementType() {
    return Association.class;
  }

  @Override
  protected String getXMLElementName() {
    return ELEMENT_ASSOCIATION;
  }

  @Override
  protected BaseElement convertXMLToElement(XMLStreamReader xtr, BpmnModel model) throws Exception {
    Association association = new Association();
    BpmnXMLUtil.addXMLLocation(association, xtr);
    association.setSourceRef(xtr.getAttributeValue(null, ATTRIBUTE_FLOW_SOURCE_REF));
    association.setTargetRef(xtr.getAttributeValue(null, ATTRIBUTE_FLOW_TARGET_REF));
    association.setId(xtr.getAttributeValue(null, ATTRIBUTE_ID));

    String asociationDirectionString = xtr.getAttributeValue(null, ATTRIBUTE_ASSOCIATION_DIRECTION);
     if (StringUtils.isNotEmpty(asociationDirectionString)) {
       AssociationDirection associationDirection = AssociationDirection.valueOf(asociationDirectionString.toUpperCase());

       association.setAssociationDirection(associationDirection);
     }

    parseChildElements(getXMLElementName(), association, model, xtr);

    return association;
  }

  @Override
  protected void writeAdditionalAttributes(BaseElement element, BpmnModel model, XMLStreamWriter xtw) throws Exception {
    Association association = (Association) element;
    writeDefaultAttribute(ATTRIBUTE_FLOW_SOURCE_REF, association.getSourceRef(), xtw);
    writeDefaultAttribute(ATTRIBUTE_FLOW_TARGET_REF, association.getTargetRef(), xtw);
    AssociationDirection associationDirection = association.getAssociationDirection();
    if (associationDirection !=null) {
      writeDefaultAttribute(ATTRIBUTE_ASSOCIATION_DIRECTION, associationDirection.getValue(), xtw);
    }
  }

  @Override
  protected void writeAdditionalChildElements(BaseElement element, BpmnModel model, XMLStreamWriter xtw) throws Exception {
  }
}
