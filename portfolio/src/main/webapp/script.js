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
/**
 * Fetches a random greeting from the server and adds it to the DOM.
 */
// function getRandomGreeting () {
//     console.log('Fetching a random greeting.');

//     const responsePromise = fetch('/random-greeting');

//     responsePromise.then(handleResponse);
// }

// function handleResponse(response) {
//     console.log('Handling the response. ');

//     const textPromise = response.text();
//     textPromise.then(addGreetingToDom);
// }
/** Adds a random greeting to the DOM. */
// function addGreetingToDom(greeting) {
//     console.log('Adding greeting to dom: ' + greeting);
//     const greetingContainer = document.getElementById('greeting-container');
//     greetingContainer.innerText = greeting;
// }

function getRandomGreeting() {
    fetch('/data').then(response => response.text()).then((greeting) => {
        document.getElementById('greeting-container').innerText = greeting;
    });
}
/**
 * Adds a random greeting to the page.
 */
//  function addRandomGreeting() {
// const greetings =
//       ['Why is abbreviation such a long word?', 'Why do they call them apartments if they are built together?', 'Who closes the bus door after the bus driver gets off?', 'Why did Sally sell seashells by the seashore when you can just pick them up anyway?'];

//   // Pick a random greeting.
//  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

   // Add it to the page.
//    const greetingContainer = document.getElementById('greeting-container');
//    greetingContainer.innerText = greeting;
// }
