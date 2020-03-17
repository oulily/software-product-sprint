// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

  // Given: Collection of known events and meeting request
  // Return: Collection<TimeRange>: times when the meeting could happen that day
  // tests that need to pass: eventSplitRestriction, overlappingEvents, justEnoughRoom,
        // ignoresPeopleNotAttending, noOptionForTooLongOfARequest, everyAttendeeIsConsidered,
        // nestedEvents

  // loop through the events make sure that it query doesn't overlap with existing events
  // make sure there's enough room for the number of attendees
  // make sure duration is not too long

  // loop through the events make sure that it query doesn't overlap with existing events
public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    int duration = (int)request.getDuration();
    ArrayList<Event> eventList = new ArrayList<>(events); 
    
    // throw new UnsupportedOperationException("TODO: Implement this method.");
    Collection<TimeRange> timeRanges = new ArrayList<>();


    int timeStart = 0;
    int timeBetween;
    int i = 0;
    
    while (i < eventList.size() && timeStart + duration <= 1440) {
        timeBetween = eventList.get(i).getWhen().start() - timeStart;
        if (timeBetween >= duration) {
            while (!attendeesOverlap(eventList.get(i), request) && i < eventList.size()) {
                i++;
                timeBetween = eventList.get(i).getWhen().start() - timeStart;
            }
            TimeRange tr = null;
            tr = tr.fromStartDuration(timeStart, timeBetween);
            timeRanges.add(tr);
        }
        timeStart = timeStart + eventList.get(i).getWhen().end();
        i++;
    }

    if (1440 - timeStart >= duration) {
        TimeRange tr = null;
        tr = tr.fromStartDuration(timeStart, 1440 - timeStart);
        timeRanges.add(tr);
    }

    System.out.println("[");
    for (TimeRange tr : timeRanges) {
        tr.toString();
    }
    System.out.println("]");
    return timeRanges;
  }

  public boolean attendeesOverlap(Event a, MeetingRequest b) {
      for (String eventAttendee : a.getAttendees()) {
          for (String requestAttendee : b.getAttendees()) {
              if (eventAttendee.equals(requestAttendee)) {
                  return true;
              }
          }
      }
      return false;
  }


}
