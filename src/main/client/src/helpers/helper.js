/**
 * Splits a binary string into chunks of specified block size.
 * This function takes a long binary string and divides it into smaller segments, each of the specified `blockSize`.
 *
 * @param {string} binaryString The binary string to be split into blocks.
 * @returns {string[]} An array of binary string blocks, each of length `blockSize`.
 */
export function formatBinary(binaryString) {
    const blockSize = 34;
    let ipBlocks = [];

    for(let i = 0; i < binaryString.length; i += blockSize) {
        ipBlocks.push(binaryString.slice(i, i + blockSize));
    }
    return ipBlocks;
}