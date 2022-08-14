/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hitpm_v2.ICES_beans_bpmn.converter.alfresco;

import hitpm_v2.ICES_beans_bpmn.converter.StartEventXMLConverter;
import hitpm_v2.ICES_beans_bpmn.model.BaseElement;
import hitpm_v2.ICES_beans_bpmn.model.alfresco.AlfrescoStartEvent;

/**

 */
public class AlfrescoStartEventXMLConverter extends StartEventXMLConverter {

  public Class<? extends BaseElement> getBpmnElementType() {
    return AlfrescoStartEvent.class;
  }


}
