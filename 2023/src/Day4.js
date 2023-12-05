const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day4.txt");
const REGEX = /[Card ]+\d+\:+ ([\w ]+) \|+ ([\w ]+)/;
const CARD_ID_REGEX = /[Card ]+(\d+)/;

const numsAsList = (numStr) => {
  return numStr.split(" ").filter(e => e !== "").map(e => Number(e.trim()));
}

const resolvePartOne = () => {
  return INPUT_LIST.reduce((score, line) => {
    const winningNumsArr = numsAsList(line.match(REGEX)[1]);
    const yourNumsArr = numsAsList(line.match(REGEX)[2]);

    const points = yourNumsArr.reduce((acc, num) => 
        winningNumsArr.includes(num) ? acc === 0 ? 1 : acc * 2 : acc, 0);
    return score + points;
  }, 0);
}

const resolvePartTwo = () => {
  const instances = {};
  for (let line of INPUT_LIST) {
    const cardId = line.match(CARD_ID_REGEX)[1];
    instances[cardId] = 1;
  }
  for (let i = 0; i < INPUT_LIST.length; i++) {
    const line = INPUT_LIST[i];
    const winningNumsArr = numsAsList(line.match(REGEX)[1]);
    const yourNumsArr = numsAsList(line.match(REGEX)[2]);

    const points = yourNumsArr.reduce((acc, num) => 
        winningNumsArr.includes(num) ? acc + 1 : acc, 0);

    for (let copyIndex = 1; copyIndex < points + 1; copyIndex++) {
      const copy = INPUT_LIST[i + copyIndex];
      const copyCardId = copy.match(CARD_ID_REGEX)[1];
      const originalCardId = line.match(CARD_ID_REGEX)[1];
      instances[copyCardId] += instances[originalCardId];
    }
  }
  return Object.entries(instances).reduce((acc, [key, value]) => acc + value, 0);
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
