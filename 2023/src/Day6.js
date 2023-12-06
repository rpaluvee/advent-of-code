const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day6.txt");

const resolveDayOne = () => {
  const times = INPUT_LIST[0].match(/Time:\s+([\w ]+)/)[1].split(/\s+/).map(e => Number(e));
  const distances = INPUT_LIST[1].match(/Distance:\s+([\w ]+)/)[1].split(/\s+/).map(e => Number(e));

  let sums = [];
  for (let i = 0; i < times.length; i++) {
    const time = times[i];
    const distance = distances[i];
    let sum = 0;
    for (let buttonHold = 1; buttonHold < time; buttonHold++) {
      const travelDistance = buttonHold * (time - buttonHold);
      if (travelDistance > distance) {
        sum++
      }
    }
    sums.push(sum);
  }
  return sums.reduce((acc, sum) => acc * sum, 1);
}

const resolveDayTwo = () => {
  const time = Number(INPUT_LIST[0].match(/Time:\s+([\w ]+)/)[1].replaceAll(" ", ""));
  const distance = Number(INPUT_LIST[1].match(/Distance:\s+([\w ]+)/)[1].replaceAll(" ", ""));

  let sum = 0;
  for (let buttonHold = 1; buttonHold < time; buttonHold++) {
    const travelDistance = buttonHold * (time - buttonHold);
    if (travelDistance > distance) {
      sum++
    }
  }
  return sum;
}

console.log(resolveDayOne());
console.log(resolveDayTwo());
