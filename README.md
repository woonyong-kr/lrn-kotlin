# lrn-kotlin

Kotlin 문법을 읽다가 헷갈린 실행 순서를 작은 프로그램으로 확인하는 저장소다. 레이블이 가리키는 대상, 객체의 동등성과 해시, 람다가 바깥 변수를 보관하는 방식처럼 코드와 출력으로 비교할 수 있는 예제를 모았다.

## 소유 경계

- Woon Wiki는 개념 설명, 출처, 학습 순서와 이해 상태를 소유한다.
- 이 저장소는 독립 실행 가능한 Kotlin source와 자동 test를 소유한다.
- 같은 설명을 두 곳에 복제하지 않는다. Wiki의 질문은 여기서 실행하고, 실행으로 확인한 개념만 Wiki 학습 기록에 남긴다.

## 현재 학습 경로

현재 예제는 *Kotlin in Action, Second Edition*의 Chapter 2, Chapter 4의 동등성과 해시, Chapter 5의 캡처·SAM·inline을 다룬다. 책 전체를 구현한 저장소는 아니다. Chapter 2는 `함수·변수 → property → Color·Expr 분기 → loop·range·collection → exception` 순서로 실행할 수 있다.

| 순서 | 개념 | 실행 source |
| --- | --- | --- |
| 2.1.1–2.1.6 | 함수, expression body, 선언·초기화, `val`·`var`, 문자열 template | `fundamentals/Fundamentals.kt` |
| 2.2.1–2.2.3 | property, custom accessor, Boolean `is...` JVM 이름, package·import | `properties/Properties.kt`, `review/Chapter02Review.kt` |
| 2.3.1 | `enum class`에 RGB 데이터와 동작 붙이기 | `Color.kt` |
| 2.3.2 | exhaustive `when`으로 색 온도 분류하기 | `WhenWithEnum.kt` |
| 2.3.3 | `when (val color = ...)`로 측정값을 한 번만 읽기 | `WhenSubjectVariable.kt` |
| 2.3.4 | `Set` equality로 순서 없는 색 조합 비교하기 | `WhenWithSets.kt` |
| 2.3.5 | argument 없는 `when`의 Boolean condition으로 같은 규칙 표현하기 | `WhenWithoutSubject.kt` |
| 2.3.6–2.3.8 | smart cast, 재대입 이후 type, exhaustive `when`, block 결과 | `expressions/ExprFlow.kt` |
| 2.4.1 | `break@label`, `return@label`, `this@label`의 대상과 실행 순서 확인하기 | `labels/LabelFlow.kt` |
| 2.4.2 | `..`, `..<`, `downTo`, `step`의 경계·방향·간격 확인하기 | `ranges/RangeProgression.kt` |
| 2.4.3–2.4.4 | Map.Entry destructuring과 오른쪽 type에 따른 `in` 의미 | `collections/MapMembership.kt` |
| 2.5.1–2.5.2 | exception 전파, `catch` fallback, `finally`, `try` expression | `exceptions/ExceptionFlow.kt` |

source 위치:

```text
src/main/kotlin/learning/kotlininaction/chapter02/colors/
src/main/kotlin/learning/kotlininaction/chapter02/collections/
src/main/kotlin/learning/kotlininaction/chapter02/exceptions/
src/main/kotlin/learning/kotlininaction/chapter02/expressions/
src/main/kotlin/learning/kotlininaction/chapter02/fundamentals/
src/main/kotlin/learning/kotlininaction/chapter02/labels/
src/main/kotlin/learning/kotlininaction/chapter02/properties/
src/main/kotlin/learning/kotlininaction/chapter02/ranges/
src/main/kotlin/learning/kotlininaction/chapter02/review/
```

## 실행

JDK 21이 필요하다. Gradle wrapper와 Kotlin JVM 2.4.10을 사용하며, 처음 실행할 때 Gradle과 의존성을 내려받는다.

```bash
./gradlew run
./gradlew runLabelFlow
./gradlew runRangeProgression
./gradlew runChapter02Review
./gradlew test
```

`runChapter02Review`는 Chapter 2의 여덟 학습 구간을 Wiki와 같은 순서로 한 번에 출력한다. `run`은 2.3.1–2.3.5 Color 흐름, `runLabelFlow`는 label 대상, `runRangeProgression`은 포함·제외 경계와 간격만 집중 실행한다. `test`는 Chapter 2와 동등성·해시 예제의 결과와 실패 조건을 검사한다. Chapter 5의 간단한 조건 검사는 해당 실행 프로그램 안에 있다.

한 개념만 확인할 때는 해당 task를 실행하면 된다. 예제를 수정했다면 관련 테스트로 결과와 실패 조건을 확인한다. 실행 출력은 그 예제의 관찰이며, 곧바로 언어 전체의 동작이나 학습자의 이해 정도를 뜻하지 않는다.

## Chapter 4: 동등성과 해시

`chapter04/equality/EqualityAndHashing.kt`는 `equals`만 구현한 객체, 의도적으로 해시 계약을 깨뜨린 객체, 같은 필드로 두 메서드를 구현한 객체, `data class`를 실제 JVM `HashSet`·`HashMap`에 넣어 비교한다. 같은 해시를 가진 다른 값(`Aa`·`BB`), 삽입 후 mutable key 변경, 변경 전에 제거하고 재삽입하는 대조도 출력한다.

```bash
./gradlew runEqualityAndHashing
./gradlew test --tests 'learning.kotlininaction.chapter04.equality.EqualityAndHashingTest'
```

같은 값이면 같은 해시여야 하지만, 같은 해시라고 같은 값인 것은 아니다. 기본 identity hash는 충돌할 수 있으므로 실패 테스트는 해시 `1`·`2`를 명시한 잘못된 구현을 사용한다. 계약 위반과 삽입 후 key 변경의 실패 출력은 실행한 JVM에서의 관찰이다. 모든 JVM이 같은 실패를 보장한다는 뜻은 아니며, `data class`도 `var`를 불변으로 바꾸지는 않는다.

계약: [Kotlin equality](https://kotlinlang.org/docs/equality.html), [data class 생성 규칙](https://kotlinlang.org/docs/data-classes.html), [JDK 21 Object.hashCode](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html#hashCode()), [Map의 mutable key와 hash 최적화](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html).

## Chapter 5: 캡처와 실제 호출

[CaptureAndCalls.kt](src/main/kotlin/learning/kotlininaction/chapter05/lambdas/CaptureAndCalls.kt)는 값 캡처, 객체 참조 캡처, 여러 람다가 공유하는 변경 가능한 변수, SAM과 inline 호출을 비교한다.

```bash
./gradlew runCaptureAndCalls
```

같은 counter를 두 번 호출한 뒤 별도로 만든 counter를 호출하면 `[1, 2, 1]`이 나온다. `apply`와 `also`는 모두 같은 객체를 반환하며, `also` 안에서도 객체의 값을 바꿀 수 있다.

Kotlin JVM 2.4.10·Temurin 21.0.8에서 소스를 컴파일하고 `javap -c -p`로 확인했다. 반환된 counter는 `Ref.IntRef`를 보관하지만 inline 예제의 지역 정수는 호출 위치의 연산으로 펼쳐졌다. `run(::salute)`도 이 컴파일 결과에서는 `salute()`를 직접 호출했다.

SAM·lambda의 참조 동일성 출력은 현재 compiler·JVM에서의 관찰이다. 객체 재사용이나 물리적인 메모리 할당 횟수의 보장으로 사용하지 않는다. [LambdaMetafactory 계약](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/invoke/LambdaMetafactory.html)도 생성된 함수 객체의 identity를 고정하지 않는다. `T.() -> R`의 receiver는 블록 안의 `this`를 정하는 함수 타입이며 inline 여부나 스택 배치와는 별개다.

## 근거

- [Kotlin in Action 2e · Chapter 2 공식 예제](https://github.com/Kotlin/kotlin-in-action-2e/tree/main/src/main/kotlin/ch02)
- [Kotlin `when` 공식 문서](https://kotlinlang.org/docs/control-flow.html#when-expressions-and-statements)
- [Kotlin return과 jump 공식 문서](https://kotlinlang.org/docs/returns.html)
- [Kotlin `setOf` 공식 API](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/set-of.html)
- [Kotlin range와 progression 공식 문서](https://kotlinlang.org/docs/ranges.html)
