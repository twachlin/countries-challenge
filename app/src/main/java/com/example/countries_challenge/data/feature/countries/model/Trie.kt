package com.example.countries_challenge.data.feature.countries.model

/**
 * TrieNode represents a node in a Trie data structure.
 * It contains a mutable map of child nodes, where the key is a character,
 * and the value is the corresponding TrieNode for that character.
 * The boolean flag 'isEndOfWord' indicates whether the current node marks the end of a word.
 */
class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    var isEndOfWord = false
}

/**
 * This implementation consists of a Trie class with methods for inserting words into the Trie
 * and searching for the existence of a specific word.
 *
 * @property root - The root node of the Trie, representing an empty string.
 */
class Trie {
    private val root = TrieNode()

    /**
     * Inserts a word into the Trie, creating nodes for each character in the word.
     * Marks the end of the word by setting the 'isEndOfWord' flag to true.
     *
     * @param word - The word to be inserted into the Trie.
     */
    fun insert(word: String) {
        var node = root
        word.lowercase().forEach { char ->
            node = node.children.computeIfAbsent(char) { TrieNode() }
        }
        node.isEndOfWord = true
    }

    /**
     * Finds and returns a list of words in the Trie that start with the given prefix.
     * @param prefix The prefix to search for.
     * @return A list of words starting with the specified prefix.
     */
    fun startsWith(prefix: String): List<String> {
        val lowerCasePrefix = prefix.lowercase()
        val result = mutableListOf<String>()
        var node = root

        // Traverse the Trie to the node representing the prefix
        lowerCasePrefix.forEach { char ->
            node = node.children[char] ?: return result
        }

        // Collect words starting from the prefix
        collectWords(node, lowerCasePrefix, result)
        return result
    }

    /**
     * Recursively collects words starting from a specified node and appends them to the result list.
     * @param node The current TrieNode being processed.
     * @param currentPrefix The prefix built up to the current node.
     * @param result The list to which the collected words are appended.
     */
    private fun collectWords(node: TrieNode, currentPrefix: String, result: MutableList<String>) {
        if (node.isEndOfWord) {
            result.add(currentPrefix)
        }

        // Recursively collect words from child nodes
        node.children.forEach { (char, childNode) ->
            collectWords(childNode, currentPrefix + char, result)
        }
    }
}