const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day3.txt");
const MATRIX = INPUT_LIST.map(row => [...row].map(col => col));
const POSITIONS_TO_CHECK = [
  {y: -1, x: -1}, {y: -1, x: 0}, {y: -1, x: 1},
  {y:  0, x: -1},                {y:  0, x: 1},
  {y:  1, x: -1}, {y:  1, x: 0}, {y:  1, x: 1},
]

const isNumber = value => !isNaN(value);
const isSymbol = value => value !== ".";
const isOutOfBounds = pos => {
  return pos.y < 0 || pos.x < 0 || pos.y > MATRIX.length - 1 || pos.x > MATRIX[pos.y].length - 1;
}

const hasAdjacentSymbol = (y, x) => {
  return POSITIONS_TO_CHECK.some(pos => {
    const checkedPos = { y: y + pos.y, x: x + pos.x };

    if (isOutOfBounds(checkedPos)) {
      return false;
    }
    const checkedValue = MATRIX[checkedPos.y][checkedPos.x];
    // same line adjacent number check
    if (checkedPos.y === y && isNumber(checkedValue)) {
      return false;
    }
    return isSymbol(checkedValue);
  });
}

const getAdjacentAsteriskPos = (y, x) => {
  for (const pos of POSITIONS_TO_CHECK) {
    const checkedPos = { y: y + pos.y, x: x + pos.x };

    if (isOutOfBounds(checkedPos)) continue;

    const checkedValue = MATRIX[checkedPos.y][checkedPos.x];
    if (checkedValue === "*") {
      return checkedPos;
    }
  }
  return null;
}

const resolvePartOne = () => {
  let sum = 0;
  let partNumber = "";
  let enginePart = false;
  for (let y = 0; y < MATRIX.length; y++) {
    for (let x = 0; x < MATRIX[y].length; x++) {
      const currentElement = MATRIX[y][x];
      if (isNumber(currentElement)) {
        enginePart = !enginePart ? hasAdjacentSymbol(y, x) : enginePart;
        partNumber += currentElement;
      }
      if ((!isNumber(currentElement) || x === MATRIX[y].length - 1) && partNumber !== "") {
        if (enginePart) {
          sum += Number(partNumber);
        }
        partNumber = "";
        enginePart = false;
      }
    }
  }
  return sum;
}

const resolvePartTwo = () => {
  let map = {};
  let partNumber = "";
  let asteriskPos = null;
  for (let y = 0; y < MATRIX.length; y++) {
    for (let x = 0; x < MATRIX[y].length; x++) {
      const currentElement = MATRIX[y][x];
      if (isNumber(currentElement)) {
        asteriskPos = asteriskPos === null ? getAdjacentAsteriskPos(y, x) : asteriskPos;
        partNumber += currentElement;
      }
      if ((!isNumber(currentElement) || x === MATRIX[y].length - 1) && partNumber !== "") {
        if (asteriskPos !== null) {
          const key = asteriskPos.x + "," + asteriskPos.y;
          map[key] = map[key] ? [...map[key], partNumber] : [partNumber];
        }
        partNumber = "";
        asteriskPos = null;
      }
    }
  }
  return Object.entries(map).reduce((acc, [key, value]) => {
    return value.length === 2 ? acc + value[0] * value[1] : acc;
  }, 0);
}

console.log(resolvePartOne());
console.log(resolvePartTwo());
