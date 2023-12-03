const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day2.txt");

const resolvePartOne = () => {
  let result = 0;
  for (const line of INPUT_LIST) {
    const gameId = Number(line.match(/Game ([0-9]+)/)[1]);
    let gameWon = true;
    for (const round of line.split(";")) {
      const regexPattern = /([0-9]+) ([red|green|blue]+)/g;
      const bag = { blue: 0, green: 0, red: 0 };
      for (const match of round.matchAll(regexPattern)) {
        const value = Number(match[1]);
        const color = match[2];
        bag[color] += value;
      }
      if (bag.blue > 14 || bag.green > 13 || bag.red > 12) {
        gameWon = false;
      }
    }
    result += gameWon ? gameId : 0;
  }
  return result;
}

const resolvePartTwo = () => {
  let result = 0;
  for (const line of INPUT_LIST) {
    const values = { blue: [], green: [], red: [] };
    for (const round of line.split(";")) {
      const regexPattern = /([0-9]+) ([red|green|blue]+)/g;
      const bag = { blue: 0, green: 0, red: 0 };
      for (const match of round.matchAll(regexPattern)) {
        const value = Number(match[1]);
        const color = match[2];
        bag[color] += value;
      }
      values.blue.push(bag.blue);
      values.green.push(bag.green);
      values.red.push(bag.red);
    }
    const maxValues = ["blue", "green", "red"].map(color => 
        Math.max(...values[color]));
    result += maxValues.reduce((acc, curr) => acc * curr, 1);
  }
  return result;
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
