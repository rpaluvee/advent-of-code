const { readFileSync } = require('node:fs');

function readFileToString(filePath) {
  try {
    const data = readFileSync(filePath);
    return data.toString();
  } catch (error) {
    console.error(`Got an error trying to read the file: ${error.message}`);
 }
}

function readFileToList(filePath) {
  try {
    const data = readFileSync(filePath).toString().split("\n");
    return data;
  } catch (error) {
    console.error(`Got an error trying to read the file: ${error.message}`);
 }
}

module.exports = { readFileToString, readFileToList };
