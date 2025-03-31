/**
 * Sends a request to the server to get the IPv4 calculation based on the provided user input data.
 * @param {Object} inputData The input data to be sent to the server.
 * @returns {Promise<Object>} A promise that resolves to the calculated data.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function getIPv4Calculation(inputData) {
  const resp = await fetch("http://localhost:8080/api/v1/ipv4-calculator", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(inputData),
  });

  // Check if the response is OK
  if (!resp.ok) {
    const errorData = await resp.json();
    throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}

/**
 * Sends a request to the server to get the IPv6 calculation based on the provided user input data.
 * @param {Object} inputData The input data to be sent to the server.
 * @returns {Promise<Object>} A promise that resolves to the calculated data.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function getIPv6Calculation(inputData) {
  const resp = await fetch("http://localhost:8080/api/v1/ipv6-calculator", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(inputData),
  });

  // Check if the response is O
  if (!resp.ok) {
    const errorData = await resp.json();
    throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}

/**
 * Sends a request to the server to get an IPv4 subnet visualizer based on the provided user input data.
 * @param {Object} inputData The input data to be sent to the server.
 * @returns {Promise<Object>} A promise that resolves to the visualized subnet data.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function getIPv4Visualizer(inputData) {
  const resp = await fetch("http://localhost:8080/api/v1/subnet-visualizer/ipv4", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(inputData),
  });

  // Check if the response is OK
  if (!resp.ok) {
    const errorData = await resp.json();
    throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}

/**
 * Fetches a practice question for the IP learning tool.
 * @returns {Promise<Object>} A promise that resolves to the practice question data.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function getIPLearntoolQuestion() {
  const resp = await fetch("http://localhost:8080/api/v1/practice-tool");

  if (!resp.ok) {
    const errorData = await resp.json();
    throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}

/**
 * Validates the user's answer for a practice question in the IP learning tool.
 * @param {Object} question The question with the provided answer to validate.
 * @returns {Promise<Object>} A promise that resolves to the validation result.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function validateIPLearntoolQuestion(question) {
  const resp = await fetch("http://localhost:8080/api/v1/practice-tool/validate", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(question),
  });

  if (!resp.ok) {
    const errorData = await resp.json();
    throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}

/**
 * Sends a request to get a quiz question related to the OSI model.
 * @param {Object} questionData The question data to be sent to the server.
 * @returns {Promise<Object>} A promise that resolves to the quiz question data.
 * @throws {Error} Throws an error if the server response indicates failure.
 */
export async function getOsiQuizQuestion(questionData) {
  const resp = await fetch("http://localhost:8080/api/v1/quiz", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify(questionData),
  });

  if (!resp.ok) {
  const errorData = await resp.json();
  throw new Error(errorData.message || "Unknown error occurred");
  }

  const data = await resp.json();
  return data;
}
