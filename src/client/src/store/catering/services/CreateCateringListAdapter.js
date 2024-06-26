import {Visibility} from "@/store/catering/services/Visibility";

export function createCateringListAdapter() {
    return {
        label: '',
        description: '',
        maxUsers: "10",
        itemLimit: "10",
        isActive: true,
        location: '',
        visibility: Visibility[0],
        notes: '',
    }
}