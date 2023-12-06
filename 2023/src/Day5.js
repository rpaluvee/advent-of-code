const { readFileToList } = require('./FileReader.js');

const INPUT_LIST = readFileToList("../input/day5.txt");

const processSeed = (seed, maps) => {
  let patternBreakList = [];
  for (const map of maps) {
    for (let i = 0; i < map.range.length; i++) {
      const destStart = map.destinationRangeStart[i];
      const srcStart = map.sourceRangeStart[i];
      const range = map.range[i];

      if (seed >= srcStart && seed <= srcStart + range - 1) {
        patternBreakList.push(range - (seed - srcStart)); // part 2 magic
        seed += destStart - srcStart;
        break;
      }
    }
  }
  return { result: seed, breaks: patternBreakList };
}

const resolveTask = () => {
  const seeds = INPUT_LIST[0].match(/seeds: ([\d ]+)/)[1].split(" ").map(e => Number(e));
  const maps = [];
  for (let i = 1; i < INPUT_LIST.length; i++) {
    const line = INPUT_LIST[i];
    if (line.includes(":")) {
      maps.push({
        destinationRangeStart: [],
        sourceRangeStart: [],
        range: []
      });
    }
    if (line !== "" && !line.includes(":")) {
      const numsSplit = line.split(" ").map(e => Number(e));
      maps[maps.length - 1].destinationRangeStart.push(numsSplit[0]);
      maps[maps.length - 1].sourceRangeStart.push(numsSplit[1]);
      maps[maps.length - 1].range.push(numsSplit[2]);
    }
  }

  // PART 1
  const seedsPart1 = seeds.map(seed => processSeed(seed, maps).result);
  console.log(Math.min(...seedsPart1));

  // PART 2
  const seedsPart2 = [];
  let patternBreak = 1;
  for (let i = 0; i < seeds.length; i += 2) {
    const start = seeds[i];
    const range = seeds[i + 1];
    // pattern will continue the same until patternBreak so we can
    // skip the seeds that fall into the same pattern.
    for (let seed = start; seed < start + range; seed += patternBreak) {
      const processResult = processSeed(seed, maps);
      seedsPart2.push(processResult.result);
      patternBreak = Math.min(...processResult.breaks);
    }
  }
  console.log(Math.min(...seedsPart2));
}

let startTime = new Date().getTime();
resolveTask();
let timeElapsed = new Date().getTime() - startTime;
console.log("Execution time: " + timeElapsed + "ms")
