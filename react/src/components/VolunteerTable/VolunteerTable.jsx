

export default function VolunteerTable(){
    const isLoggedIn = false;

    return (
        <table>
            <thread>
                <tr>
                    <th>Name:</th>
                    <th>Email:</th>
                    <th>Phone:</th>
                    {isLoggedIn}
                </tr>
            </thread>
            <tbody>
                {volunteers.map((volunteer) => (
                    <tr key={volunteer.volunteerId}>
                        <td>{volunteer.name}</td>
                        <td>{volunteer.email}</td>
                        <td>{volunteer.phone}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}