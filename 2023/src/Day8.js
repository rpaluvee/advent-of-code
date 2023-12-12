const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day8.txt");
const REGEX = /([\w\d]{3}) = \(([\w\d]{3}), ([\w\d]{3})\)/;

const resolvePartOne = () => {
  const instructions = INPUT_LIST[0].split("");
  let map = {};
  for (let line of INPUT_LIST.slice(2)) {
    let node = line.match(REGEX)[1];
    let left = line.match(REGEX)[2];
    let right = line.match(REGEX)[3];
    map[node] = [left, right];
  }
  let curr = "AAA";
  let count = 0;
  while (curr !== "ZZZ") {
    for (let instruction of instructions) {
      curr = map[curr][instruction === "L" ? 0 : 1];
      count++;
      if (curr === "ZZZ") break;
     }
  }
  return count;
}

const resolvePartTwo = () => {
  const instructions = INPUT_LIST[0].split("");
  let map = {};
  for (let line of INPUT_LIST.slice(2)) {
    let node = line.match(REGEX)[1];
    let left = line.match(REGEX)[2];
    let right = line.match(REGEX)[3];
    map[node] = [left, right];
  }
  const startingNodes = Object.entries(map)
      .filter(([key, value]) => key.endsWith("A"))
      .map(([key, value]) => key);

  let counts = [];
  for (let node of startingNodes) {
    let count = 0;
    let resultNode = node;
    while (!resultNode.endsWith("Z")) {
      for (let instruction of instructions) {
        resultNode = map[resultNode][instruction === "L" ? 0 : 1];
        count++;
        if (resultNode.endsWith("Z")) {
          counts.push(count);
          break;
        }
      }
    }
  }
  return lcm(...counts);
}

const lcm = (...numbers) => {
  return numbers.reduce((a, b) => a * b / gcd(a, b));
}

const gcd = (...numbers) => {
  return numbers.reduce((a, b) => {
      while (b) {
          let t = b;
          b = a % b;
          a = t;
      }
      return a;
  });
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
