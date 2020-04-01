/**
 * Copyright (C) 2015 MonTier Software (2015) LTD - All Rights Reserved
 * 
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * 
 * 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.mon-tier.com/static/MonTier-EULA.pdf
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Written by MonTier Software Team <contactsus@mon-tier.com>, September 2015
 */
package il.co.menora.soaarchive.client.events;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientEventBus {
	private static final ResettableEventBus eventBus = new ResettableEventBus(new SimpleEventBus());
	private static final HashMap<Widget, ArrayList<HandlerRegistration>> handlersRegistrationsMap = new HashMap<Widget, ArrayList<HandlerRegistration>>();

	public static ResettableEventBus get() {
		return eventBus;
	}

	// Helper function to add an event handler on a widget's onLoad
	public static <E extends EventHandler> void registerEventHandlerOnLoad(Widget widget, Type<E> type, E eventHandler) {
		HandlerRegistration widgetHandlerRegistration = eventBus.addHandler(type, eventHandler);
		ArrayList<HandlerRegistration> widgetHandlerRegistrations = handlersRegistrationsMap.get(widget);
		if (widgetHandlerRegistrations == null) {
			widgetHandlerRegistrations = new ArrayList<HandlerRegistration>();
			handlersRegistrationsMap.put(widget, widgetHandlerRegistrations);
		}
		widgetHandlerRegistrations.add(widgetHandlerRegistration);
	}

	// Helper function to remove all event handler of a widget on its onUnload
	public static void removeEventHandlersOnUnload(Widget widget) {
		ArrayList<HandlerRegistration> widgetHandlerRegistrations = handlersRegistrationsMap.get(widget);
		if (widgetHandlerRegistrations != null) {
			for (HandlerRegistration widgetHandlerRegistration : widgetHandlerRegistrations) {
				widgetHandlerRegistration.removeHandler();
			}
			handlersRegistrationsMap.remove(widget);
		}
	}
}
