package learning.kotlininaction.chapter05.lambdas

private class Box(var value: Int)

private fun valueCapture(seed: Int): () -> Int {
    val value = seed
    return { value }
}

private fun referenceCapture(box: Box): () -> Int {
    val reference = box
    return { reference.value }
}

private fun makeCounter(): () -> Int {
    var count = 0
    return { ++count }
}

private fun sharedCounter(): Pair<() -> Int, () -> Int> {
    var count = 0
    return Pair({ ++count }, { count })
}

// A top-level property is static state, not a captured local variable.
private var hits = 0

private fun objectRunnable(): Runnable = object : Runnable {
    override fun run() { hits++ }
}

private fun nonCapturingSam(): Runnable = Runnable { hits++ }
private fun capturingSam(box: Box): Runnable = Runnable { box.value++ }
private fun nonCapturingLambda(): () -> Unit = { hits++ }
private fun capturingLambda(box: Box): () -> Unit = { box.value++ }

private inline fun invokeInline(block: () -> Unit) { block() }

private fun inlineCase(): Int {
    var count = 0
    invokeInline { count++ }
    return count
}

// Receiver syntax does not make this function inline.
private fun withReceiver(box: Box, block: Box.() -> Unit) { box.block() }

private fun receiverCase(box: Box) {
    withReceiver(box) { value += 3 }
}

private fun salute() { println("salute") }

private fun referenceCase() {
    println("before run")
    run(::salute)
    println("after run")
}

fun main() {
    println("stdlib=${KotlinVersion.CURRENT}; JVM=${System.getProperty("java.runtime.version")}")

    var input = 7
    val readValue = valueCapture(input)
    input = 99
    check(readValue() == 7)
    println("val capture=${readValue()}; caller input=$input")

    val box = Box(7)
    val readReference = referenceCapture(box)
    box.value = 9
    check(readReference() == 9)
    println("reference capture=${readReference()}")

    val counter = makeCounter()
    val counterValues = listOf(counter(), counter(), makeCounter()())
    check(counterValues == listOf(1, 2, 1))
    println("counter, counter, separate counter=$counterValues")

    val (increment, read) = sharedCounter()
    val sharedValues = listOf(increment(), read(), increment(), read(), sharedCounter().second())
    check(sharedValues == listOf(1, 1, 2, 2, 0))
    println("shared increment/read; separate read=$sharedValues")

    val target = Box(0)
    val objectTask = objectRunnable()
    val sam = nonCapturingSam()
    val capturedSam = capturingSam(target)
    val lambda = nonCapturingLambda()
    val capturedLambda = capturingLambda(target)
    // Identity is observed for this runtime; SAM/lambda reuse is not asserted.
    println("same instance: object=${objectTask === objectRunnable()}")
    println("same instance: SAM noncapturing=${sam === nonCapturingSam()}, capturing=${capturedSam === capturingSam(target)}")
    println("same instance: lambda noncapturing=${lambda === nonCapturingLambda()}, capturing=${capturedLambda === capturingLambda(target)}")
    objectTask.run()
    sam.run()
    capturedSam.run()
    lambda()
    capturedLambda()
    check(hits == 3 && target.value == 2)
    println("static hits=$hits; captured object=${target.value}")

    val inlineResult = inlineCase()
    receiverCase(target)
    check(inlineResult == 1 && target.value == 5)
    println("inline local=$inlineResult; non-inline receiver=${target.value}")
    referenceCase()

    val receiver = Box(1)
    val applied = receiver.apply { value = 2 }
    val alsoResult = receiver.also { it.value += 3 }
    check(applied === receiver && alsoResult === receiver && receiver.value == 5)
    println("apply same=${applied === receiver}; also same=${alsoResult === receiver}; mutated value=${receiver.value}")
}
