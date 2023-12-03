const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day1.txt");
const NUMBERS = "0123456789";
const NUMBERS_AS_STRINGS = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"];

const resolvePartOne = () =>
  INPUT_LIST.reduce((sum, line) => {
    const nums = [...line].reduce((result, i) =>
        NUMBERS.includes(i) ? result += i : result,
        "");
    const firstNum = String(nums[0]);
    const lastNum = String(nums[nums.length - 1]);
    const resultNum = Number.parseInt(firstNum + lastNum);
    return sum += resultNum;
  }, 0);

const resolvePartTwo = () =>
  INPUT_LIST.reduce((sum, line) => {
    const processedLine = [...line].reduce((result, i) => {
      result += i;
      NUMBERS_AS_STRINGS.map((numStr, index) => {
        result = result.replace(numStr, (index + 1) + i);
      });
      return result;
    }, "");
    const nums = [...processedLine].reduce((result, i) =>
        NUMBERS.includes(i) ? result += i : result,
        "");
    const firstNum = String(nums[0]);
    const lastNum = String(nums[nums.length - 1]);
    const resultNum = Number.parseInt(firstNum + lastNum);
    return sum += resultNum;
  }, 0);

console.log(resolvePartOne());
console.log(resolvePartTwo());
