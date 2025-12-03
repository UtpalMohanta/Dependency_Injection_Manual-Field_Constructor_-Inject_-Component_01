🔥 DI আসলে কী?
Dependency Injection মানে হলো —
যে class আরেকটা class-এর ওপর নির্ভর করে (dependency), সেই dependency-টা class-এর ভিতরে না বানিয়ে বাইরে থেকে দিয়ে দেওয়া।

👉 সহজ ভাষায়:
"যার যা দরকার, সেটা তাকে বানাতে না দিয়ে বাইরে থেকে রেডি করে দিয়ে দেওয়া"

🎯 কেন দরকার?
কারণ এতে—
1️⃣ Code test করা সহজ হয়
Class-এর মধ্যে new Something() করলে mock দেওয়া যায় না।
DI থাকলে dependency বাইরে থেকে দেওয়া যায় → testing সহজ।
2️⃣ Code clean থাকে
Class শুধু নিজের কাজ করবে, dependency বানিয়ে সময় নষ্ট করবে না।
3️⃣ Reusability বাড়ে
একই dependency অনেক জায়গায় ব্যবহার করা যায়।
4️⃣ Loose Coupling তৈরি হয়
Class গুলো একে অপরের সাথে শক্তভাবে বাঁধা থাকে না।

🍔 সহজ উদাহরণ (Food Example)
ধরো তুমি একটা রেস্টুরেন্টে কাজ করো—
তোমার কাজ Burger Serve করা।
❌ Bad (No DI)
তুমি বার্গার সার্ভ করার আগে নিজে প্যাটি, বান, সস—সব বানাতে শুরু করলে।
→ সময় যাবে
→ রেস্টুরেন্ট স্লো
→ তুমি ক্লান্ত 😅

✅ Good (With DI)
রান্নাঘর রেডি বার্গার করে তোমাকে দেয় → তুমি শুধু সার্ভ করো।
Dependency = Burger
You = Server class
Kitchen = Dependency Provider

💻 Simple Kotlin Example (DI ছাড়া)
class Engine {
    fun start() = "Engine started"
}
class Car {
    private val engine = Engine()   // ❌ Car নিজে engine বানাচ্ছে
    fun run() = engine.start()
}


এখন সমস্যা কী?
Car class Engine-এর সাথে শক্তভাবে বাঁধা
Test করা কঠিন
Engine পরিবর্তন করতে পারলে Car-ও পরিবর্তন করতে হবে

💻 Simple Kotlin Example (Constructor DI সহ)
class Engine {
    fun start() = "Engine started"
}
class Car(private val engine: Engine) {   // ✅ বাইরে থেকে dependency inject
    fun run() = engine.start()
}
// Somewhere else
val engine = Engine()
val car = Car(engine)


এখন সুবিধা:
Car Engine বানাচ্ছে না → dependency outside থেকে আসছে
Test করলে fake engine দিতে পারো
Engine বদলালেও Car পরিবর্তন করা লাগবে না

🎉 Dependency Injection এক লাইনে
“Class নিজে dependency তৈরি না করে, বাইরে থেকে dependency supply করা—এটাই Dependency Injection।”
✅ 1) val repository = UserRepository()
এটার মানে:
👉 তুমি নিজেই নতুন একটা UserRepository বানাচ্ছো।
Example:
val repository = UserRepository()
এটা করলে—
প্রতিবার নতুন অবজেক্ট তৈরি হবে
class নিজে dependency বানাবে
Test করা কঠিন
এটা No Dependency Injection

✅ 2) val repository: UserRepository
এটার মানে:
👉 তুমি বলছো আমার repository নামে একটা variable আছে, কিন্তু তুমি এখনো তাকে মান দাওনি।
Example:
class UserViewModel(val repository: UserRepository)

এখানে repository: UserRepository শুধু type declare করছে।
মান পরে বাইরে থেকে দেওয়া হবে।
এটা Dependency Injection।

📌 Dependency মানে:
একটা ক্লাস আরেকটা ক্লাস ছাড়া কাজ করতে না পারা।
যেটা ছাড়া কাজ হবে না → সেটাই Dependency।

🔥 Dagger 2 কী?
Dagger 2 হলো Android (এবং Java) প্রোজেক্টে Dependency Injection করার জন্য একটা খুব দ্রুত, শক্তিশালী DI Framework।
👉 এটা compile time-এ কোড জেনারেট করে, তাই super fast।

🎯 কেন Dagger 2 ব্যবহার করা হয়?

Dependency গুলো স্বয়ংক্রিয়ভাবে supply করে
Lifetime control করা যায় (Singleton, ActivityScope etc.)
Test করা সহজ হয়
Code clean ও scalable হয়
Constructor injection + Module + Component খুব powerful

🍔 একটা Real Life Example
ধরো:
তোমার কাছে Car আছে
Car এর দরকার Engine
❌ যদি Car নিজে Engine বানায়:
class Car {
    val engine = Engine()   // Bad (No DI)
}
✅ Dagger Engine তৈরি করে Car-কে দেবে:
class Car @Inject constructor(val engine: Engine)
হ্যাঁ! শুধু @Inject constructor দিলেই Dagger Car-এর dependency চিনে যাবে।

🔹 Step 1: Dependency Classes
1️⃣ Engine (Inject করা যাবে)
import javax.inject.Inject
class Engine @Inject constructor() {
    fun start(): String {
        return "Engine started"
    }
}
👉 @Inject constructor() মানে:
এই ক্লাসটা Dagger বানাতে পারবে।

2️⃣ Driver (এটাও dependency)
import javax.inject.Inject
class Driver @Inject constructor() {
    fun getName(): String = "Mr. John"
}

3️⃣ Car (Engine + Driver দরকার)
import javax.inject.Inject
class Car @Inject constructor( private val engine: Engine, private val driver: Driver) {
    fun drive(): String {
        return "${driver.getName()} is driving… ${engine.start()}"
    }
}


👉 Car-এর dependency হলো Engine + Driver
👉 Dagger এগুলো সরবরাহ করবে

🔹 Step 2 (Important): Module

এখন ধরো, Fuel class আছে যেটার constructor-এ @Inject দিতে পারছো না
(যেমন Retrofit, OkHttp, SharedPreferences এগুলা constructor inject করা যায় না)

তাই আমরা Module ব্যবহার করবো।

4️⃣ Fuel (constructor inject করা যাবে না)
class Fuel (val amount: Int)

5️⃣ Fuel Module
import dagger.Module
import dagger.Provides

@Module
class FuelModule {
    @Provides
    fun provideFuel(): Fuel {
        return Fuel(100)
    }
}
👉 Module Dagger-কে শেখায় Fuel কিভাবে বানাতে হবে।

🔹 Step 3: Component
এখন সব Dependency connect করা হবে Component দিয়ে।
import dagger.Component
@Component(modules = [FuelModule::class])
interface CarComponent {
    fun getCar(): Car  
    fun getFuel(): Fuel
}
👉 Component = Dagger-এর “Dependency Supplier Machine”

🔹 Step 4: Build & Use
Kotlin Main() বা Android App-এ:
fun main() {
    val component = DaggerCarComponent.create()
    val car = component.getCar()
    val fuel = component.getFuel()
    println(car.drive())
    println("Fuel amount: ${fuel.amount}")
}
👉 Output:
Mr. John is driving… Engine started
Fuel amount: 100

🔹 Car আর Engine Example
Car class কে drive করতে Engine দরকার → Car-এর dependency Engine
Car class-এ @Inject constructor(private val engine: Engine) ব্যবহার করি → Car বুঝবে Engine supply করতে হবে
কিন্তু Engine class-এ কেন @Inject constructor() লাগে?
উত্তর:
Dagger-কে বলতে হবে “Engine কিভাবে বানানো হবে”
class Engine @Inject constructor() {
    fun start() = "Engine started"
}
Engine class নিজেই কোন dependency লাগবে না
কিন্তু Dagger-কে যখন Car বানাতে হবে → Car engine চাইছে
Dagger যদি জানে Engine-কে কোনভাবে বানানো যায় → তখন Car build হবে
তাই Engine class-এ @Inject constructor() লাগে
🎯 সহজ কথায়:
Car: Engine দরকার → Car class-এ @Inject
Engine: নিজেই simple → Engine class-এ @Inject constructor() দিলে Dagger জানবে “আমি Engine বানাতে পারি”
analogy:
তুমি restaurant-এ burger বানাতে চাও (Car)
Burger-এর জন্য beef লাগবে (Engine)
Dagger = Chef
যদি chef জানে beef কোথা থেকে আসবে (Engine class-এ @Inject) → chef Car বানাতে পারবে
না হলে chef Car বানাতে পারবে না

🚀 @Module কি?
@Module হলো Dagger-কে বলার একটি জায়গা, “এই জিনিসগুলো আমি তৈরি করতে পারি”।
সাধারণত third-party class বা এমন class যেটাতে আমরা @Inject constructor() দিতে পারি না
যেমন: Retrofit, OkHttpClient, SharedPreferences, Room database
Dagger কে এখানে @Provides method দিয়ে বলি কিভাবে বানাতে হবে

🔹 Basic Example
ধরো আমাদের Fuel class আছে, constructor inject দেওয়া যায় না:
class Fuel(val amount: Int)
এখন আমরা চাই Dagger Fuel supply করুক।
Fuel Module
import dagger.Module
import dagger.Provides

@Module
class FuelModule {
    @Provides
    fun provideFuel(): Fuel {
        return Fuel(100)
    }
}
ব্যাখ্যা:
@Module → Dagger কে বলে এই class-এর ভিতরে dependency বানানোর rule আছে
@Provides → Dagger কে বলে কীভাবে Fuel বানানো হবে

🔹 Component-এর সাথে use করা
import dagger.Component
@Component(modules = [FuelModule::class])
interface CarComponent {
    fun getFuel(): Fuel
}
fun main() {
    val component = DaggerCarComponent.create()
    val fuel = component.getFuel()
    println(fuel.amount)  // Output: 100
}

🔹 কেন সব class-এ @Inject constructor দেওয়া যায় না?
কারণ @Inject constructor ব্যবহার করতে হলে class-এর constructor আমাদের হাতে থাকতে হবে।
১️⃣ Third-party class
যেমন: Retrofit, OkHttpClient, Room database, SharedPreferences
আমরা নিজে এই class-এর কোড লিখিনি → constructor modify করতে পারি না
তাই এখানে @Inject constructor দিতে পারি না
val retrofit = Retrofit.Builder()  // আমরা constructor modify করতে পারি না
২️⃣ কিছু class খুব special হয়ে বানানো
ধরো class-এর constructor private
বা factory method ব্যবহার করে বানানো
বা singleton pattern ব্যবহার করছে
এগুলোতে @Inject constructor কাজ করবে না

🔹 সমাধান: Module + @Provides
আমরা Dagger কে একটা rule দিতে পারি → “এই class কিভাবে বানাতে হবে”
Dagger তারপর supply করবে

@Module
class NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com")
            .build()
    }
}
এখানে Retrofit আমরা inject করতে পারলাম na, কারণ constructor modify করতে পারিনি, তাই Provides method দিয়ে বানানোর নিয়ম দিয়েছি।

analogy 🍔
তুমি chef (Dagger)
Retrofit = imported ingredient (তুমি নিজে বানাতে পারছ না)
Module = recipe বই → chef কে বলে “এটা কিভাবে বানানো হবে”
Provides = সেই page যেখানে step-by-step বানানোর নিয়ম
Chef Module দেখে ingredient বানাবে, Car বানাবে, সব ঠিক হবে|

✔ Component কি?

Dagger Component হলো—একটা Bridge / Connection।
এটা Inject করা object তৈরি করে এবং তোমার class-এ পাঠায়।

সহজ ভাষায়:
👉 “Module-এ কীভাবে object বানাতে হবে—তা Component জানে এবং সেই object দিয়ে তোমার class বানায়।”
✔ Very Simple Component Example
🔹 1. Dependency Class
class Engine @Inject constructor() {
    fun start() = "Engine Started"
}
🔹 2. Class that needs dependency
class Car @Inject constructor(
    private val engine: Engine
) {
    fun startCar() = engine.start()
}
🔹 3. Component
@Component
interface CarComponent {
    fun getCar(): Car
}
🔹 4. Use the Component
fun main() {
    val component = DaggerCarComponent.create()
    val car = component.getCar()
    println(car.startCar())
}

Output:
Engine Started

🔥 Component Summary:
@Component = Dagger-এর Factory
তোমার @Inject constructor দেখে object বানায়
তোমার class-এ dependency inject করে
অ্যাপের সব dependency কে connect করে

@Binds (Interface Binding)
✔ কেন @Binds লাগে?

কারণ Interface সরাসরি object হয় না।
তাই Dagger কে বলতে হয়—
👉 “এই interface-এর implementation এটা।”

✔ Example Without Binds (Error হবে)
Interface:
interface UserRepo {
    fun getUser(): String
}
Implementation:
class LocalUserRepo @Inject constructor() : UserRepo {
    override fun getUser() = "User from Local DB"
}
Inject:
class UserViewModel @Inject constructor( private val repo: UserRepo ) {
    fun showUser() = repo.getUser()
}
⚠ Error:
Dagger বুঝবে না → UserRepo = কোন implementation?

🔥 Solution = @Binds
Module:
@Module
interface RepoModule {
    @Binds
    fun bindUserRepo(impl: LocalUserRepo): UserRepo
}
👉 এটা মানে:
“UserRepo চাইলে LocalUserRepo দেবে।”
Component:
@Component(modules = [RepoModule::class])
interface AppComponent {
    fun getViewModel(): UserViewModel
}
Run:
fun main() {
    val component = DaggerAppComponent.create()
    val vm = component.getViewModel()
    println(vm.showUser())
}
Output:
User from Local DB

🔹 Scope / Lifetime কি?
Scope / Lifetime = অবজেক্ট কতক্ষণ বাঁচবে এবং কখন destroy হবে
Dagger 2-তে dependency সবসময় নতুন করে বানানো হয়, যদি তুমি কোনো scope define না করো
Scope দিয়ে control করো → একই object reuse হবে

🔹 Common Scopes
Scope	Meaning	Use Case
@Singleton	App চলার সময় শুধু 1 object	Repository, Retrofit, Database
@ActivityScoped	Activity চালু থাকা পর্যন্ত object বাঁচবে	Analytics tracker, Activity-level dependency
@FragmentScoped	Fragment চালু থাকা পর্যন্ত object বাঁচবে	Fragment-level ViewModel বা Tracker

🔹 Example 1: Singleton
import javax.inject.Singleton
import dagger.Component
import dagger.Module
import dagger.Provides

class Repository {
    fun getData() = "Data from Repository"
}

@Module
class RepoModule {
    @Provides
    @Singleton
    fun provideRepository(): Repository = Repository()
}
@Singleton
@Component(modules = [RepoModule::class])
interface AppComponent {
    fun getRepository(): Repository
}
fun main() {
    val component = DaggerAppComponent.create()
    val repo1 = component.getRepository()
    val repo2 = component.getRepository()
    println(repo1 === repo2)  // Output: true (একই object)
}✅ @Singleton দিলে একই Repository সব জায়গায় reuse হবে

🔹 Example 2: No Scope (নতুন object প্রতি inject)
@Module
class RepoModule2 {
    @Provides
    fun provideRepository(): Repository = Repository()
}
@Component(modules = [RepoModule2::class])
interface AppComponent2 {
    fun getRepository(): Repository
}
fun main() {
    val component = DaggerAppComponent2.create()
    val repo1 = component.getRepository()
    val repo2 = component.getRepository()
    println(repo1 === repo2)  // Output: false (প্রতিবার নতুন object)
}✅ No scope → প্রতিবার নতুন object বানানো হবে

🔹 এই লাইনটা:
fun getData() = "Data from Repository"

এটার মানে কি?
✅ ১. এটা হলো একটা ফাংশন (function)
যার নাম → getData()
✅ ২. ফাংশনটি কোন কাজ করে?
এটা শুধু একটা স্ট্রিং রিটার্ন করে → "Data from Repository"

@Named &  @Provides
🔥 Problem: একই টাইপের দুইটা dependency inject করতে গেলে Confusion হয়

ধরো তুমি দুইটা API client বানালে:
val client1 = Retrofit.Builder()...
val client2 = Retrofit.Builder()...

দুইটাই Retrofit
কিন্তু দুইটা আলাদা কাজের জন্য:
একটা → Local API
একটা → Remote API

এখন যদি Car, Repository বা অন্য class Retrofit চাই
Dagger বুঝবে না → কোন Retrofit দিব? Local না Remote?
এটাই conflict / ambiguity।

🔥 Solution = Qualifier
Qualifier মানে → “ট্যাগ লাগানো”
যাতে Dagger বুঝে কোনটা কোনটা।

🔹 1. @Named ব্যবহার করা (সবচেয়ে সহজ)
>>>>>>✔ Provides
@Module
class NetworkModule {
    @Provides
    @Named("Local")
    fun provideLocalRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://local.example.com")
            .build()
    }
    @Provides
    @Named("Remote")
    fun provideRemoteRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://remote.example.com")
            .build()
    }
}
<<<<<<✔ Inject
class Repository @Inject constructor(
    @Named("Local") private val localApi: Retrofit,
    @Named("Remote") private val remoteApi: Retrofit
) {
    fun getLocalData() = localApi.create(Api::class.java)
    fun getRemoteData() = remoteApi.create(Api::class.java)
}

👉 Dagger এখন clear:
@Named("Local") → Local Retrofit
@Named("Remote") → Remote Retrofit

No confusion ✔

🔥 2. Custom Qualifier (যখন @Named ব্যবহার করতে চাই না)
নিজের annotation বানাতে পারো।
✔ Step 1: Qualifier Annotation তৈরি
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalApi

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteApi

>>>>> ✔ Step 2: Module এ Tag করা
@Module
class NetworkModule {
    @Provides
    @LocalApi
    fun provideLocalRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://local.example.com")
            .build()
    }
    @Provides
    @RemoteApi
    fun provideRemoteRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://remote.example.com")
            .build()
    }
}
>>>>>✔ Step 3: Inject
class Repository @Inject constructor(
    @LocalApi private val localApi: Retrofit,
    @RemoteApi private val remoteApi: Retrofit
)

🔥 কোনটা ব্যবহার করা ভালো?
@Named	সহজ project, দ্রুত কাজ
Custom Annotation	বড় project, readable, scalable

🔥 সহজ উদাহরণ — পানি কিন্তু দুই ধরণের 🍶
দুটো বোতলেই পানি আছে
কিন্তু একটার লেবেল “ঠান্ডা”
আরেকটার লেবেল “গরম”
Qualifier = সেই লেবেল 💯

🔥 Lazy / Provider কী?

Dependency Injection-এ
Lazy / Provider ব্যবহার করা হয় যখন আমরা কোনো dependency তখনই বানাতে চাই যখন সত্যিই দরকার।
মানে:
App চালুর সময় heavy object বানিয়ে RAM খরচ না করা
যখন ফাংশন call হবে তখন object বানানো
এইটাকেই বলে lazy loading।

🔥 কেন লাগবে Lazy / Provider?
ধরো তুমি এমন object বানাচ্ছ:
Retrofit
Database
Big list
File reader
Image processor
এই জিনিসগুলো heavy, মানে তৈরি করতে সময় লাগে + memory লাগে।
অথচ তোমার class এ dependency থাকলেও, তুমি সেই dependency সবসময় ব্যবহার করো না।
তাই একেই বলা হয়:
👉 “আগে থেকেই বানিও না, দরকার হলে বানাও।”

🔥 1️⃣ Lazy (Dagger Lazy)
Dagger-এর Lazy<T> object কে cache করে রাখে।
প্রথমবার call করলে object বানাবে
পরে আর বানাবে না—আগেরটাই reuse করবে

>>>>>✔ Example
Class:
class Car @Inject constructor(
    private val engine: Lazy<Engine>
) {
    fun startCar() {
        println("Car ready… but engine not started yet!")
        engine.get().start() // engine এখানেই প্রথমবার বানানো হবে
    }
}
Engine:
class Engine @Inject constructor() {
    init {
        println("Engine created!")
    }
    fun start() {
        println("Engine Started!")
    }
}

✔ Output হবে:
Car ready… but engine not started yet!
Engine created!
Engine Started!

Engine object Car create হওয়ার সময়ে বানানো হয়নি,
startCar() ফাংশন কল করার সময় বানানো হয়েছে।

🔵 Topic 1: What is Hilt?
🔥 Hilt = Dependency Injection (DI) কে Android-এ খুব সহজ করে দেয়।
Dagger কঠিন → অনেক কম্পোনেন্ট, মডিউল, boilerplate
Hilt সহজ → Android lifecycle অনুযায়ী সব ready-made support

⚡ Android app এ dependency inject করা খুব সহজ হয়:
Activity/Fragment/Service → কয়েকটা annotation দিলেই dependency চলে আসে
ViewModel inject করা super easy
Retrofit/Room/Repository সব inject করা সহজ

✔️ With Hilt (auto injection)
Step 1: App class
@HiltAndroidApp
class MyApp : Application()

Step 2: Activity inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var car: Car
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(car.drive())
    }
}
Step 3: Dependency classes
class Engine @Inject constructor() {
    fun start() = "Engine started"
}
class Car @Inject constructor(private val engine: Engine) {
    fun drive() = engine.start()
}
🔥 @HiltAndroidApp কী?
এটা এমন একটি annotation যেটা Application ক্লাসে দিলে Hilt পুরো অ্যাপে নিজের জন্য
DI container তৈরি করে
1.Component গুলো বানায়
2.Dependency graph setup করে
👉 সহজ ভাষায়:
“Hilt কে বলি — এই অ্যাপে কাজ শুরু করো।”

🟢 কেন এটা লাগে?
Android app শুরু হয় Application class থেকে।
তাই Hilt চায়:
"আমাকে আগে Application ক্লাসে ঢুকতে দাও, তারপর আমি পুরো অ্যাপে dependency inject করবো।"

🟣 Example 
Step 1: Application class তৈরি করো
@HiltAndroidApp
class MyApp : Application()
👉 শুধু এই এক লাইন দিলে Hilt backend-এ নিজের জন্য Component বানিয়ে প্রস্তুত হয়ে যায়।

🟡 যদি এটা না দাও?
❌ Hilt কাজ করবে না
❌ কোন Activity/Fragment এ injection পাওয়া যাবে না
❌ @Inject, @Module সবই error দিবে

🔥 Real Life Example (প্রকৃত প্রজেক্টে)
যখন তুমি Retrofit, Room, Repository inject করতে চাও, Hilt আগে Application এ rooted না হলে এগুলো app-wide কাজ করবে না।
📝 Summary 
@HiltAndroidApp দিলে Hilt পুরো অ্যাপের জন্য DI container বানায়।

🔵 Topic 3: @AndroidEntryPoint
(Activity/Fragment এ dependency ঢোকানোর দরজা)
🔥 @AndroidEntryPoint কী?
এই annotation দিলে Hilt ওই Activity/Fragment/Service/Receiver এর ভিতরে dependency inject করতে পারে।
👉 সহজ ভাষায়:
“Hilt, এটার ভিতরে dependency পাঠাতে পারো।”

🟢 কেন লাগে?
তুমি যদি Activity বা Fragment–এ @Inject ব্যবহার করতে চাও, তাহলে অবশ্যই @AndroidEntryPoint দিতে হবে।
না দিলে error → “Hilt cannot inject this class”
🔥 কোন জায়গায় @AndroidEntryPoint লাগে?
Activity, Fragment, View, Service, BroadcastReceiver, Fragment inside Fragment, Hilt-enabled ViewModels

👉 Basically যেখানে @Inject variable লাগে, সেখানে এই annotation দেওয়া লাগে।
❌ কোথায় লাগে না?
সাধারণ class, Repository, UseCase, Utils, Retrofit service, Room DAO
এগুলোতে শুধু constructor-এ @Inject দিলেই হয়।

🔵 Topic 4: @Inject (Constructor Injection)
(Dependency ইনজেক্ট করার সবচেয়ে সহজ, সুন্দর এবং powerful পদ্ধতি)

🔥 @Inject কী?
👉 এটা এমন একটি annotation যেটা দিলে Hilt বুঝে যায়:
“এই ক্লাসটা বানাতে হলে এর ভিতরের dependency গুলো আমিই তৈরি করবো।”
মানে object manually নতুন করে বানাতে হবে না।
🟢 কেন এটা লাগে?
Constructor Injection = Hilt class-এর object তৈরি করে
→ constructor-এ যেটা লাগবে সেটা automatic দেয়।

🔵 Topic 5: Hilt Components (Scopes + Lifetime সহজভাবে)
এই টপিকটা খুব গুরুত্বপূর্ণ, কারণ dependency কতক্ষণ বাঁচবে (lifetime) সেটা Component ঠিক করে।
🔥 Hilt Components কী?
Hilt-এ কিছু predefined components আছে।
প্রতিটি component Android lifecycle অনুযায়ী dependency ধরে রাখে।

👉 সহজ ভাষায়:
“App-এর কোন লেভেলে কোন dependency কতক্ষণ থাকবে— এটা component ঠিক করে।”
🟢 Hilt-এর Built-In Components (সহজ ব্যাখ্যা)
Component	                 Lifetime	            কোথায় ব্যবহার
SingletonComponent	পুরো অ্যাপ চলা পর্যন্ত	Retrofit, Room, Repository, prefManager
ActivityRetainedComponent	Activity destroy → recreate হলেও থাকে	ViewModel
ActivityComponent	Activity destroy হওয়া পর্যন্ত	Activity-specific dependency
FragmentComponent	Fragment destroy হওয়া পর্যন্ত	Fragment dependency
ViewModelComponent	ViewModel এর lifetime	UseCase, Repository inside ViewModel
ViewComponent	View destroy হওয়া পর্যন্ত	Custom View
ServiceComponent	Service stop হওয়া পর্যন্ত	Foreground Service dependency

🟣 Super Simple Example — SingletonComponent
Repository সারা অ্যাপে একটাই থাকবে
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideRepository(): UserRepository {
        return UserRepository()
    }
}
👉 এই object অ্যাপ বন্ধ না হওয়া পর্যন্ত বেঁচে থাকবে।

🔵 Topic 6: @Module + @InstallIn
(যেখানে @Inject constructor ব্যবহার করা যায় না—সেখানে dependency Provide করার সিস্টেম)
🔥 @Module কী?
👉 সহজ ভাষায়:
Module = Dependency বানানোর কারখানা।
যে dependency constructor-এ @Inject দিতে পারো না → সেটা Module এর ভিতরে বানাতে হয়।
🔥 @InstallIn কী?
Module কোথায় install হবে → কোন Component এর under এ dependency থাকবে → সেটা বলে দেয়।
👉 সহজ ভাষায়:
InstallIn = Dependency কতক্ষণ বাঁচবে সেটা ঠিক করা।
🟣 কখন Module লাগে? (Important)
Constructor এ @Inject দেওয়া যায় না যখন:
❌ third-party class, ❌ Retrofit, ❌ Room database, ❌ OkHttp, ❌ SharedPreferences, ❌ Firebase, ❌ Interfaces → Implementation, ❌ Custom builder class
এসবের object Hilt নিজে জানে না, তাই Module দিয়ে বানানোর rule দিতে হয়।
🟢 Example 1 — Retrofit Provide করা
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .build()
    }
}
✔ Retrofit @Inject constructor নেই
✔ তাই @Provides দিয়ে object বানাচ্ছি
✔ SingletonComponent → Retrofit পুরো অ্যাপে একটাই থাকবে

🔵 Topic 7: @Binds — Interface → Implementation Inject করা
(Hilt-এ Interface এর জন্য dependency bind করার সবচেয়ে সহজ পদ্ধতি)
🔥 @Binds কী?
👉 এটা এমন একটা annotation যেটা Hilt-কে বলে:
“এই Interface চাইলে এই Implementation দিও।”
মানে:
Interface → কোন ক্লাস ব্যবহার হবে সেটা fix করা।
🟣 কেন এটা লাগে?
Android-এ Interface বেশি use করা হয়:
Clean Architecture
Repository Pattern
UseCase
Abstraction
Testing easy করার জন্য
কিন্তু Hilt Interface এর object বানাতে পারে না, কারণ Interface-এর constructor নেই।
তাই Hilt কে বলতে হয়—
“এই Interface-এর implementation হলো এই ক্লাস।”
🟢 Simple Example (Super Easy)
Step 1: Interface
interface Logger {
    fun log(message: String)
}
Step 2: Implementation class
class FileLogger @Inject constructor() : Logger {
    override fun log(message: String) {
        println("File Log: $message")
    }
}
Step 3: Binds Module
@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {
    @Binds
    abstract fun bindLogger(impl: FileLogger): Logger
}
👉 বলছে:
যখনই Logger প্রয়োজন হবে → Hilt automatically FileLogger দেবে।

🔵 Activity তে use করা
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var logger: Logger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.log("Hello Hilt!")
    }
}
Output:
File Log: Hello Hilt!

🟡 @Binds এর Rules
✔ abstract function হতে হবে
✔ Module class → abstract class হতে হবে
✔ Argument = implementation
✔ Return type = interface
