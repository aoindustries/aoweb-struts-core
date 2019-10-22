/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2019  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.html.Doctype;
import com.aoindustries.html.Serialization;
import com.aoindustries.util.MinimalList;
import static com.aoindustries.website.ApplicationResources.accessor;
import java.util.List;
import java.util.Locale;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.ValidationMessage;

/**
 * @author  AO Industries, Inc.
 */
public class SkinTagTEI extends TagExtraInfo {

	@Override
	public ValidationMessage[] validate(TagData data) {
		List<ValidationMessage> messages = MinimalList.emptyList();
		Object serializationAttr = data.getAttribute("serialization");
		if(
			serializationAttr != null
			&& serializationAttr != TagData.REQUEST_TIME_VALUE
		) {
			String serialization = ((String)serializationAttr).trim();
			if(!serialization.isEmpty() && !"auto".equalsIgnoreCase(serialization)) {
				try {
					Serialization.valueOf(serialization.toUpperCase(Locale.ROOT));
				} catch(IllegalArgumentException e) {
					messages = MinimalList.add(
						messages,
						new ValidationMessage(data.getId(), com.aoindustries.taglib.ApplicationResources.accessor.getMessage("HtmlTag.serialization.invalid", serialization))
					);
				}
			}
		}
		Object doctypeAttr = data.getAttribute("doctype");
		if(
			doctypeAttr != null
			&& doctypeAttr != TagData.REQUEST_TIME_VALUE
		) {
			String doctype = ((String)doctypeAttr).trim();
			if(!doctype.isEmpty() && !"default".equalsIgnoreCase(doctype)) {
				try {
					Doctype.valueOf(doctype.toUpperCase(Locale.ROOT));
				} catch(IllegalArgumentException e) {
					messages = MinimalList.add(
						messages,
						new ValidationMessage(data.getId(), com.aoindustries.taglib.ApplicationResources.accessor.getMessage("HtmlTag.doctype.invalid", doctype))
					);
				}
			}
		}
		Object layoutAttr = data.getAttribute("layout");
		if(
			layoutAttr != null
			&& layoutAttr != TagData.REQUEST_TIME_VALUE
		) {
			String layout = ((String)layoutAttr).trim();
			if(!PageAttributes.LAYOUT_NORMAL.equals(layout) && !PageAttributes.LAYOUT_MINIMAL.equals(layout)) {
				messages = MinimalList.add(
					messages,
					new ValidationMessage(
						data.getId(),
						accessor.getMessage(
							//"Invalid value for layout, must be either \"normal\" or \"minimal\"",
							//Locale.getDefault(),
							"skintags.SkinTagTEI.validate.layout.invalid"
						)
					)
				);
			}
		}
		return messages.isEmpty() ? null : messages.toArray(new ValidationMessage[messages.size()]);
	}
}
